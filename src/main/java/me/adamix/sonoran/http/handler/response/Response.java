package me.adamix.sonoran.http.handler.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

public interface Response {
    record Success(int statusCode, @NotNull String body) implements Response {
        public @NotNull JsonElement jsonBody() {
            return JsonParser.parseString(body);
        }
    }

    record Error(@NotNull java.lang.Exception exception) implements Response {
    }
}