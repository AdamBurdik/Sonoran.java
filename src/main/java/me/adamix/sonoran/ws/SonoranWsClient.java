package me.adamix.sonoran.ws;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.TransportEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.adamix.sonoran.SonoranURL;
import me.adamix.sonoran.ws.event.registry.EventTypeRegistry;
import me.adamix.sonoran.ws.event.WSEvent;
import me.adamix.sonoran.ws.exception.WSAuthException;
import me.adamix.sonoran.ws.exception.WSConnectionException;
import me.adamix.sonoran.ws.response.AuthResponse;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Builder(builderClassName = "Builder")
public class SonoranWsClient {
    private static final Gson gson = new Gson();

    private final String apiKey;
    private final String communityId;
    private final Integer serverId;
    private final String url;
    private final HubConnection connection;
    @Getter
    private final EventTypeRegistry registry;

    private final Map<Class<? extends WSEvent>, Consumer<WSEvent>> listeners = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T extends WSEvent> @NotNull SonoranWsClient on(@NotNull Class<T> clazz, @NotNull Consumer<T> listener) {
        listeners.put(
                clazz,
                (Consumer<WSEvent>) listener
        );
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends WSEvent> void run(@NotNull T event) {
        listeners.get(event.getClass())
                .accept(event);
    }


    public static class Builder {
        private HubConnection connection = null;
        private EventTypeRegistry registry = null;

        public Builder url(@NotNull SonoranURL url) {
            this.url = url.getUrl();
            return this;
        }

        private HubConnection createConnection() {
            return HubConnectionBuilder.create(url)
                    .withTransport(TransportEnum.WEBSOCKETS)
                    .shouldSkipNegotiate(true)
                    .build();
        }

        public SonoranWsClient build() {
            if (this.connection == null) {
                this.connection = createConnection();
            }
            if (this.registry == null) {
                this.registry = new EventTypeRegistry();
            }
            return new SonoranWsClient(apiKey, communityId, serverId, url, connection, registry);
        }
    }

    public SonoranWsClient connect() {
        try {
            connection.start().blockingAwait();
        } catch (Exception e) {
            throw new WSConnectionException("Failed to establish connection", e);
        }

        authenticate();

        return this;
    }

    private void authenticate() {
        AuthResponse response;
        try {
            response = connection.invoke(
                    AuthResponse.class,
                    WSMethods.AUTHENTICATE_V2.method(),
                    communityId,
                    apiKey,
                    serverId
            ).blockingGet();
        } catch (Exception e) {
            throw new WSConnectionException("Failed to invoke authentication", e);
        }

        if (!response.success) {
            throw new WSAuthException(response.error);
        }

        log.info("Authenticated, count={}", response.count);
        connection.on(WSMethods.PUSH_EVENTS.method(), this::handleRaw, String.class);
    }

    private void handleRaw(@NotNull String raw) {
        JsonObject json = JsonParser.parseString(raw).getAsJsonObject();
        JsonObject data = json.get("data").getAsJsonObject();

        String type = json.get("type").getAsString();

        Class<? extends WSEvent> clazz = registry.getType(type);
        if (clazz == null) {
            log.warn("Unknown event type: {}", type);
            return;
        }

        Consumer<WSEvent> handler = listeners.get(clazz);
        if (handler == null) {
            log.warn("No handler for event type: {}", type);
            return;
        }

        handler.accept(gson.fromJson(data, clazz));
    }
}