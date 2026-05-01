package me.adamix.sonoran.http.exception;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(int statusCode, String responseBody, String message) {
        super(statusCode, responseBody, message);
    }
}
