package me.adamix.sonoran.http;

import alpine.json.codec.Codec;
import com.google.common.util.concurrent.RateLimiter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
import me.adamix.sonoran.transcoder.GsonTranscoder;
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

@Slf4j
@SuppressWarnings("UnstableApiUsage")
public class SonoranRequestService {
    private static final Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();

    private final SonoranHttpService httpService;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private final Map<String, RateLimiter> limiters = new ConcurrentHashMap<>();

    private final String baseUrl;
    private final Map<String, String> defaultHeaders;

    public SonoranRequestService(
            @NotNull SonoranHttpService httpService,
            @NotNull String baseUrl,
            @NotNull Map<String, String> defaultHeaders
    ) {
        this.httpService = httpService;
        this.baseUrl = baseUrl;
        this.defaultHeaders = defaultHeaders;
    }

    private @NotNull RateLimiter getRateLimiter(@NotNull SonoranRequest request) {
        return limiters.computeIfAbsent(request.getUrl(), _ -> RateLimiter.create((double) request.getRateLimit() / 60));
    }

    public <T> @NotNull CompletableFuture<T> sendRequest(
            @NotNull SonoranRequest request,
            @NotNull Params params,
            @NotNull Codec<T> responseCodec
    ) {
        return CompletableFuture.supplyAsync(() -> {
            String url = null;
            long startTimeNanos = System.nanoTime();
            try {
                RateLimiter limiter = getRateLimiter(request);
                limiter.acquire();

                Map<String, String> headers = new HashMap<>(defaultHeaders);
                headers.putAll(request.getHeaders());

                // Final url with base, endpoint and path variables
                url = baseUrl + params.resolveUrl(request.getUrl());
                log.info("Sending request method={} url={}", request.getMethod(), url);

                // TODO Verify the params
                for (ParamDefinition param : request.getParams()) {
                    String paramKey = param.getKey();

                    if (!params.hasParam(paramKey) && param.isRequired()) {
                        throw new MissingRequiredParamException(paramKey);
                    }

                    Object paramObject = params.getParam(paramKey);
                    if (paramObject == null) continue;

                    if (!paramObject.getClass().equals(param.getType())) {
                        throw new InvalidParamTypeException(
                                "Parameter '%s' expects %s but instead got %s"
                                        .formatted(paramKey, param.getType().getName(), paramObject.getClass().getName())
                        );
                    }
                }

                HttpResponse<String> response = httpService.sendRequest(
                        request.getMethod(),
                        url,
                        headers,
                        params
                );

                long elapsedMs = (System.nanoTime() - startTimeNanos) / 1_000_000;
                log.info("Response received status={} url={} elapsedMs={}", response.statusCode(), url, elapsedMs);
                log.debug("Response body url={} status={} body={}", url, response.statusCode(), response.body());

                if (response.statusCode() >= 200 && response.statusCode() <= 299) {
                    JsonElement element = JsonParser.parseString(response.body());
                    return responseCodec.decode(GsonTranscoder.INSTANCE, element);
                }

                String body = response.body();
                throw switch (response.statusCode()) {
                    case 400 -> new BadRequestException(response.statusCode(), body, "Bad request: " + body);
                    case 401 ->
                            new UnauthorizedException(response.statusCode(), body, "Invalid authorization: " + body);
                    case 403 -> new ForbiddenException(response.statusCode(), body, "Access forbidden: " + body);
                    case 404 -> new NotFoundException(response.statusCode(), body, "Resource not found: " + body);
                    case 429 -> new RateLimitException(response.statusCode(), body, "Rate limit exceeded: " + body);
                    default -> response.statusCode() >= 500
                            ? new ServerException(response.statusCode(), body, "Server error: " + body)
                            : new ApiException(response.statusCode(), body, "Request failed (status_code: " + response.statusCode() + "): " + body);
                };

            } catch (IOException e) {
                log.error("JSON processing failed method={} url={}", request.getMethod(), url, e);
                throw new CompletionException(e);
            } catch (InterruptedException e) {
                log.error("Request interrupted method={} url={}", request.getMethod(), url, e);
                throw new CompletionException(e);
            } catch (MissingRequiredParamException e) {
                log.error("Missing required request parameter method={} url={}", request.getMethod(), url, e);
                throw new CompletionException(e);
            } catch (InvalidParamTypeException e) {
                log.error("Invalid request parameter type method={} url={}", request.getMethod(), url, e);
                throw new CompletionException(e);
            }
        }, executor);
    }
}
