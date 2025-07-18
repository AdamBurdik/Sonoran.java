package me.adamix.sonoran.http.handler.response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

public interface Response {
	record Success(int statusCode, @NotNull String body) implements Response {
		public @NotNull JsonObject jsonBody() {
			return JsonParser.parseString(body).getAsJsonObject();
		}
	}

	record Error(@NotNull java.lang.Exception exception) implements Response {}
}