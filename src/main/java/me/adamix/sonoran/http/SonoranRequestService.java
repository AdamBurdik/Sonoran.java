package me.adamix.sonoran.http;

import com.google.common.util.concurrent.RateLimiter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import me.adamix.sonoran.http.exception.ApiException;
import me.adamix.sonoran.http.exception.BadRequestException;
import me.adamix.sonoran.http.exception.ForbiddenException;
import me.adamix.sonoran.http.exception.InvalidParamTypeException;
import me.adamix.sonoran.http.exception.MissingRequiredParamException;
import me.adamix.sonoran.http.exception.NotFoundException;
import me.adamix.sonoran.http.exception.RateLimitException;
import me.adamix.sonoran.http.exception.ServerException;
import me.adamix.sonoran.http.exception.UnauthorizedException;
import me.adamix.sonoran.http.param.ParamDefinition;
import me.adamix.sonoran.http.param.Params;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@SuppressWarnings("UnstableApiUsage")
public class SonoranRequestService {
    private final Gson gson;
    private final SonoranHttpService httpService;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "sonoran-rate-limiter");
        t.setDaemon(true);
        return t;
    });
    private final Map<String, EndpointQueue> queues = new ConcurrentHashMap<>();

    private final String baseUrl;
    private final Map<String, String> defaultHeaders;

    public SonoranRequestService(
            @NotNull Gson gson,
            @NotNull SonoranHttpService httpService,
            @NotNull String baseUrl,
            @NotNull Map<String, String> defaultHeaders
    ) {
        this.gson = gson;
        this.httpService = httpService;
        this.baseUrl = baseUrl;
        this.defaultHeaders = defaultHeaders;
    }

    private static class EndpointQueue {
        private final long intervalMs;
        private long nextPermitAt = 0;

        EndpointQueue(int rateLimit) {
            this.intervalMs = 60_000L / rateLimit;
        }

        // Returns how many ms to wait before firing
        synchronized long reserveAndGetDelay() {
            long now = System.currentTimeMillis();
            if (nextPermitAt < now) nextPermitAt = now;
            long delay = nextPermitAt - now;
            nextPermitAt += intervalMs;
            return delay;
        }
    }

    private EndpointQueue getQueue(@NotNull SonoranRequest request) {
        return queues.computeIfAbsent(
                request.getUrl(),
                _ -> new EndpointQueue(request.getRateLimit())
        );
    }

    private CompletableFuture<Void> acquirePermit(@NotNull SonoranRequest request) {
        long delayMs = getQueue(request).reserveAndGetDelay();
        if (delayMs <= 0) return CompletableFuture.completedFuture(null);
        CompletableFuture<Void> future = new CompletableFuture<>();
        scheduler.schedule(() -> future.complete(null), delayMs, TimeUnit.MILLISECONDS);
        return future;
    }

    public <T> @NotNull CompletableFuture<T> sendRequest(
            @NotNull SonoranRequest request,
            @NotNull Params params,
            @NotNull TypeToken<T> typeToken
    ) {
        // Validate params eagerly, before acquiring a permit
        for (ParamDefinition param : request.getParams()) {
            String key = param.getKey();
            if (!params.hasParam(key) && param.isRequired())
                throw new MissingRequiredParamException(key);
            Object value = params.getParam(key);
            if (value != null && !param.getType().isAssignableFrom(value.getClass()))
                throw new InvalidParamTypeException(
                        "Parameter '%s' expects %s but got %s"
                                .formatted(key, param.getType().getName(), value.getClass().getName())
                );
        }

        return acquirePermit(request)
                .thenApplyAsync(_ -> {
                    String url = null;
                    long startNanos = System.nanoTime();
                    try {
                        url = baseUrl + params.resolveUrl(request.getUrl());
                        log.info("Sending request method={} url={}", request.getMethod(), url);

                        Map<String, String> headers = new HashMap<>(defaultHeaders);
                        headers.putAll(request.getHeaders());

                        HttpResponse<String> response = httpService.sendRequest(
                                request.getMethod(), url, headers, params
                        );

                        long elapsedMs = (System.nanoTime() - startNanos) / 1_000_000;
                        log.info("Response received status={} url={} elapsedMs={}",
                                response.statusCode(), url, elapsedMs);
                        log.debug("Response body url={} status={} body={}",
                                url, response.statusCode(), response.body());

                        if (response.statusCode() >= 200 && response.statusCode() <= 299)
                            return gson.fromJson(response.body(), typeToken);

                        String body = response.body();
                        throw switch (response.statusCode()) {
                            case 400 -> new BadRequestException(response.statusCode(), body, "Bad request: " + body);
                            case 401 -> new UnauthorizedException(response.statusCode(), body, "Invalid authorization: " + body);
                            case 403 -> new ForbiddenException(response.statusCode(), body, "Access forbidden: " + body);
                            case 404 -> new NotFoundException(response.statusCode(), body, "Resource not found: " + body);
                            case 429 -> new RateLimitException(response.statusCode(), body, "Rate limit exceeded: " + body);
                            default -> response.statusCode() >= 500
                                    ? new ServerException(response.statusCode(), body, "Server error: " + body)
                                    : new ApiException(response.statusCode(), body, "Request failed (" + response.statusCode() + "): " + body);
                        };

                    } catch (IOException e) {
                        log.error("JSON processing failed method={} url={}", request.getMethod(), url, e);
                        throw new CompletionException(e);
                    } catch (InterruptedException e) {
                        log.error("Request interrupted method={} url={}", request.getMethod(), url, e);
                        throw new CompletionException(e);
                    }
                }, executor);
    }

    public void shutdown() {
        executor.shutdown();
        scheduler.shutdown();
    }
}
