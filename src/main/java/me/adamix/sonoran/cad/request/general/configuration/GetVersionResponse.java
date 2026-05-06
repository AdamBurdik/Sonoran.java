package me.adamix.sonoran.cad.request.general.configuration;

import org.jetbrains.annotations.NotNull;

public record GetVersionResponse(
        int version,
        @NotNull String name
) {
}
