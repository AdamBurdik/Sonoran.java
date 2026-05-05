package me.adamix.sonoran.http.exception;

import org.jetbrains.annotations.NotNull;

public class MissingRequiredParamException extends Exception {
    public MissingRequiredParamException(@NotNull String key) {
        super("Missing required parameter: " + key);
    }
}
