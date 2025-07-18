package me.adamix.sonoran.cad.data;

import me.adamix.sonoran.http.handler.response.Response;
import org.jetbrains.annotations.NotNull;

public interface Result<T> {
	record Success<T>(@NotNull T value, @NotNull Response.Success response) implements Result<T> {}

	record Error<T>(int statusCode, @NotNull String message) implements Result<T> {}

	record Exception<T>(@NotNull java.lang.Exception exception) implements Result<T> {
	}
}
