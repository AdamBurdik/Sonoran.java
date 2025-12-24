package me.adamix.sonoran.http.handler;

import com.google.common.util.concurrent.RateLimiter;
import me.adamix.sonoran.cad.SonoranCad;
import me.adamix.sonoran.http.handler.response.Response;
import me.adamix.sonoran.http.payload.Payload;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class DefaultRequestHandler implements RequestHandler {
    private final AtomicBoolean isProcessing = new AtomicBoolean(false);
    private final AtomicBoolean scheduledShutdown = new AtomicBoolean(false);
    private final CloseableHttpClient httpClient;
    private final LinkedBlockingDeque<Task> queue = new LinkedBlockingDeque<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final RateLimiter rateLimiter;

    public DefaultRequestHandler(@NotNull CloseableHttpClient httpClient, @NotNull RateLimiter rateLimiter) {
        this.httpClient = httpClient;
        this.rateLimiter = rateLimiter;
    }

    public DefaultRequestHandler() {
        this(HttpClients.createDefault(), RateLimiter.create(SonoranCad.PERMITS_PER_SECOND));
    }

    @Override
    public void scheduleShutdown() {
        scheduledShutdown.set(true);
    }

    public void shutdown() throws IOException {
        httpClient.close();
        executor.shutdown();
    }

    @Override
    public void scheduleRequest(
            @NotNull String url,
            @NotNull Payload payload,
            @NotNull Map<String, Object> headers,
            @NotNull Consumer<Response> consumer
    ) {
        queue.addLast(new Task(url, payload, headers, consumer));
        if (!isProcessing.get()) {
            startProcessing();
        }
    }

    @Override
    public @NotNull Response sendPostRequest(
            @NotNull String url,
            @NotNull Payload payload,
            @NotNull Map<String, Object> headers
    ) {
        try {
            HttpPost post = new HttpPost(url);
            headers.forEach(post::addHeader);

            post.setEntity(payload.getEntity());

            try (CloseableHttpResponse response = httpClient.execute(post)) {
                String responseBody = EntityUtils.toString(response.getEntity());

                return new Response.Success(response.getCode(), responseBody);

            }
        } catch (Exception e) {
            return new Response.Error(e);
        }
    }

    private void startProcessing() {
        isProcessing.set(true);
        executor.submit(() -> {
            while (!queue.isEmpty()) {

                Task task = queue.poll();
                if (task == null) {
                    break;
                }

                rateLimiter.acquire();

                Response response = sendPostRequest(task.url, task.payload, task.headers);
                if (response instanceof Response.Success success) {
                    if (success.statusCode() == 429) {
                        queue.addFirst(task);
                        rateLimiter.acquire((int) (SonoranCad.PERMITS_PER_SECOND * 2));
                    }
                }

                task.consumer.accept(response);
            }
            isProcessing.set(false);

            if (scheduledShutdown.get()) {
                try {
                    shutdown();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private record Task(@NotNull String url, @NotNull Payload payload, @NotNull Map<String, Object> headers,
                        @NotNull Consumer<Response> consumer) {
    }
}
