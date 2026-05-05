package me.adamix.sonoran.ws.event.registry;

import me.adamix.sonoran.ws.event.CommunityLinkVerifiedEvent;
import me.adamix.sonoran.ws.event.WSEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class EventTypeRegistry {
    private final Map<String, Class<? extends WSEvent>> eventTypes = Map.of(
            "EVENT_COMMUNITY_LINK_VERIFIED", CommunityLinkVerifiedEvent.class
    );

    @ApiStatus.Internal
    public void register(@NotNull String key, @NotNull Class<? extends WSEvent> clazz) {

    }

    public @Nullable Class<? extends WSEvent> getType(@NotNull String key) {
        return eventTypes.get(key);
    }
}
