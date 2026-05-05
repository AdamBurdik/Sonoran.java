package me.adamix.sonoran.http.exception;

public class ForbiddenException extends ApiException {
    public ForbiddenException(int statusCode, String responseBody, String message) {
        super(statusCode, responseBody, message);
    }
}
