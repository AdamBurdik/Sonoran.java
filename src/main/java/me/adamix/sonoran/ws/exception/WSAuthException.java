package me.adamix.sonoran.ws.exception;

public class WSAuthException extends WSException {
    public WSAuthException() {
    }

    public WSAuthException(String message) {
        super(message);
    }

    public WSAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public WSAuthException(Throwable cause) {
        super(cause);
    }

    public WSAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
