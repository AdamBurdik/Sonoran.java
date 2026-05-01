package me.adamix.sonoran.http.exception;

public class BadRequestException extends ApiException {
    public BadRequestException(int statusCode, String responseBody, String message) {
        super(statusCode, responseBody, message);
    }
}
