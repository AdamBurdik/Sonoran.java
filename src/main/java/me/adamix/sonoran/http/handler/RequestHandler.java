package me.adamix.sonoran.http.handler;

import me.adamix.sonoran.http.handler.response.Response;
import me.adamix.sonoran.http.payload.Payload;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

public interface RequestHandler {
	void scheduleShutdown();

	void scheduleRequest(
			@NotNull String url,
			@NotNull Payload payload,
			@NotNull Map<String, Object> headers,
			@NotNull Consumer<Response> consumer
	);

	@NotNull Response sendPostRequest(
			@NotNull String url,
			@NotNull Payload payload,
			@NotNull Map<String, Object> headers
	);
}
