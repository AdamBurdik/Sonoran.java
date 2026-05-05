package me.adamix.sonoran.http.exception;

public class RateLimitException extends ApiException {
    public RateLimitException(int statusCode, String responseBody, String message) {
        super(statusCode, responseBody, message);
    }
}
