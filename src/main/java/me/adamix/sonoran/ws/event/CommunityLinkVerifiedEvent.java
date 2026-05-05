package me.adamix.sonoran.ws.event;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record CommunityLinkVerifiedEvent(
        @NotNull String communityUserId,
        @NotNull UUID accountUuid
) implements WSEvent {
}
