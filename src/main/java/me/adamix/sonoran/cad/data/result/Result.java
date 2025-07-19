package me.adamix.sonoran.cad.data.result;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.adamix.sonoran.cad.data.result.error.ApiError;
import me.adamix.sonoran.http.handler.response.Response;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
	private enum State { PENDING, SUCCESS, ERROR, EXCEPTION }

	private State state = State.PENDING;
	private @Nullable T successValue = null;
	private @Nullable ApiError errorValue = null;
	private @Nullable Exception exceptionValue = null;

	private final List<Consumer<T>> successListeners = new ArrayList<>();
	private final List<Consumer<ApiError>> errorListeners = new ArrayList<>();
	private final List<Consumer<Exception>> exceptionListeners = new ArrayList<>();

	public synchronized @NotNull Result<T> onSuccess(@NotNull Consumer<T> consumer) {
		if (state == State.SUCCESS && successValue != null) {
			consumer.accept(successValue);
		} else if (state == State.PENDING) {
			successListeners.add(consumer);
		}
		return this;
	}

	public synchronized @NotNull Result<T> onError(@NotNull Consumer<ApiError> consumer) {
		if (state == State.ERROR && errorValue != null) {
			consumer.accept(errorValue);
		} else if (state == State.PENDING) {
			errorListeners.add(consumer);
		}
		return this;
	}

	public synchronized @NotNull Result<T> onException(@NotNull Consumer<Exception> consumer) {
		if (state == State.EXCEPTION && exceptionValue != null) {
			consumer.accept(exceptionValue);
		} else if (state == State.PENDING) {
			exceptionListeners.add(consumer);
		}
		return this;
	}

	public synchronized void completeSuccess(@NotNull T value) {
		if (state != State.PENDING) return;
		state = State.SUCCESS;
		successValue = value;
		for (Consumer<T> c : successListeners) {
			c.accept(value);
		}
		clearListeners();
	}

	public synchronized void completeError(@NotNull ApiError error) {
		if (state != State.PENDING) return;
		state = State.ERROR;
		errorValue = error;
		for (Consumer<ApiError> c : errorListeners) {
			c.accept(error);
		}
		clearListeners();
	}

	public synchronized void completeException(@NotNull Exception exception) {
		if (state != State.PENDING) return;
		state = State.EXCEPTION;
		exceptionValue = exception;
		for (Consumer<Exception> c : exceptionListeners) {
			c.accept(exception);
		}
		clearListeners();
	}

	public synchronized void completeFromStringResponse(@NotNull Response response, @NotNull Function<@NotNull String, @NotNull T> parser) {
		switch (response) {
			case Response.Error err -> completeException(err.exception());
			case Response.Success success when success.statusCode() != 200 ->
					completeError(new ApiError(success.statusCode(), success.body()));
			case Response.Success success ->
					completeSuccess(parser.apply(success.body()));
			default -> throw new IllegalStateException("Unexpected value: " + response);
		}
	}

	public synchronized void completeFromJsonResponse(@NotNull Response response, @NotNull Function<@NotNull JsonElement, @NotNull T> parser) {
		switch (response) {
			case Response.Error err -> completeException(err.exception());
			case Response.Success success when success.statusCode() != 200 ->
					completeError(new ApiError(success.statusCode(), success.body()));
			case Response.Success success ->
					completeSuccess(parser.apply(success.jsonBody()));
			default -> throw new IllegalStateException("Unexpected value: " + response);
		}
	}

	private void clearListeners() {
		successListeners.clear();
		errorListeners.clear();
		exceptionListeners.clear();
	}

	public synchronized <U> @NotNull Result<U> flatMap(@NotNull Function<T, Result<U>> mapper) {
		Result<U> nextResult = new Result<>();
		this.onSuccess(value -> {
			try {
				Result<U> mappedResult = mapper.apply(value);
				mappedResult.onSuccess(nextResult::completeSuccess)
						.onError(nextResult::completeError)
						.onException(nextResult::completeException);
			} catch (Exception ex) {
				nextResult.completeException(ex);
			}
		});
		this.onError(nextResult::completeError);
		this.onException(nextResult::completeException);
		return nextResult;
	}
}
