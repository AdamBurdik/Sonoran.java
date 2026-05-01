package me.adamix.sonoran.http.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final int statusCode;
    private final String responseBody;

    public ApiException(int statusCode, String responseBody, String message) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }
}