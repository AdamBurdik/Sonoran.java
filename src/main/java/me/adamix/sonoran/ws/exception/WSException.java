package me.adamix.sonoran.ws.exception;

public class WSException extends RuntimeException {
    public WSException() {
    }

    public WSException(String message) {
        super(message);
    }

    public WSException(String message, Throwable cause) {
        super(message, cause);
    }

    public WSException(Throwable cause) {
        super(cause);
    }

    public WSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
