package me.adamix.sonoran.http;

import me.adamix.sonoran.http.param.Params;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class SonoranHttpService {
    private final HttpClient httpClient;

    public SonoranHttpService(@NotNull HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public SonoranHttpService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public @NotNull HttpResponse<String> sendRequest(
            @NotNull Method method,
            @NotNull String url,
            @NotNull Map<String, String> headers,
            @NotNull Params params
    ) throws IOException, InterruptedException {
        if (method.isSafe()) {
            return sendWithQuery(method, url, headers, params.toQueryString());
        } else {
            return sendWithBody(method, url, headers, params.toJson());
        }
    }

    public @NotNull HttpResponse<String> sendWithQuery(
            @NotNull Method method,
            @NotNull String url,
            @NotNull Map<String, String> headers,
            @NotNull String queryString
    ) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url + queryString))
                .method(method.name(), HttpRequest.BodyPublishers.noBody());

        headers.forEach(builder::header);

        HttpRequest request = builder.build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public @NotNull HttpResponse<String> sendWithBody(
            @NotNull Method method,
            @NotNull String url,
            @NotNull Map<String, String> headers,
            @NotNull String body
    ) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method(method.name(), HttpRequest.BodyPublishers.ofString(body));

        headers.forEach(builder::header);

        HttpRequest request = builder.build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public @NotNull HttpResponse<String> sendGetRequest(
            @NotNull String url,
            @NotNull Map<String, String> headers
    ) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET();

        headers.forEach(builder::header);

        HttpRequest request = builder.build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public @NotNull HttpResponse<String> sendPostRequest(
            @NotNull String url,
            @NotNull Map<String, String> headers,
            @NotNull String body
    ) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(body));

        headers.forEach(builder::header);

        HttpRequest request = builder.build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
