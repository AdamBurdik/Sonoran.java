package me.adamix.sonoran.cad.data.result.error;

import org.jetbrains.annotations.NotNull;

public record ApiError(int code, @NotNull String message) {
}
