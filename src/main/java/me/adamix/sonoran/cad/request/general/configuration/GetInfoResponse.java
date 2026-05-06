package me.adamix.sonoran.cad.request.general.configuration;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public record GetInfoResponse(
        @NotNull UUID uuid,
        @NotNull Community community,
        @NotNull List<String> codes
) {
    public record Community(
            @NotNull String id,
            @NotNull String name,
            @NotNull String timezone,
            @NotNull String customLoginUrl
    ) {

    }
}
