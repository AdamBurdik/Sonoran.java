package me.adamix.sonoran.ws.event.registry;

import me.adamix.sonoran.ws.event.CommunityLinkVerifiedEvent;
import me.adamix.sonoran.ws.event.WSEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class EventTypeRegistry {
    private final Map<String, Class<? extends WSEvent>> eventTypes = new HashMap<>();

    public EventTypeRegistry() {
        eventTypes.putAll(Map.of(
                "EVENT_COMMUNITY_LINK_VERIFIED", CommunityLinkVerifiedEvent.class
        ));
    }

    public void register(@NotNull String key, @NotNull Class<? extends WSEvent> clazz) {
        eventTypes.put(key, clazz);
    }

    public @Nullable Class<? extends WSEvent> getType(@NotNull String key) {
        return eventTypes.get(key);
    }
}
