package me.adamix.sonoran.http.exception;

public class InvalidParamTypeException extends RuntimeException {
    public InvalidParamTypeException(String message) {
        super(message);
    }

    public InvalidParamTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParamTypeException(Throwable cause) {
        super(cause);
    }

    protected InvalidParamTypeException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
