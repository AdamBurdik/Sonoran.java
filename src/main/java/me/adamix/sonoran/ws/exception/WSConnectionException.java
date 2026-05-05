package me.adamix.sonoran.ws.exception;

public class WSConnectionException extends WSException {
    public WSConnectionException() {
    }

    public WSConnectionException(String message) {
        super(message);
    }

    public WSConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public WSConnectionException(Throwable cause) {
        super(cause);
    }

    public WSConnectionException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
