package me.adamix.sonoran.http.exception;

public class NotFoundException extends ApiException {
    public NotFoundException(int statusCode, String responseBody, String message) {
        super(statusCode, responseBody, message);
    }
}
