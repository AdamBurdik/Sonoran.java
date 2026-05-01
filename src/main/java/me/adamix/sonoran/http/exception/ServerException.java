package me.adamix.sonoran.http.exception;

public class ServerException extends ApiException {
    public ServerException(int statusCode, String responseBody, String message) {
        super(statusCode, responseBody, message);
    }
}
