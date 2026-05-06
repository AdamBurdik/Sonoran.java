package me.adamix.sonoran.cad.request.general.accounts;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;

public record CreateCommunityLinkResponse(
        @NotNull String code,
        @NotNull String communityUserId,
        @NotNull UUID communityUuid,
        @NotNull Instant expiresAt,
        int expiresInSeconds
) {
}
