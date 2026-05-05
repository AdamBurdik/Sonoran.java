# CAD WS Client

Docs: [Sonoran WS Docs](https://docs.sonoransoftware.com/cad/api-integration/websocket-api)

Sonoran CAD provides useful websocket api.

You can directly listen to events or push events with smaller rate limits.

---

## Setup

To interact with the web sockets, SonoranWSClient is needed.
You can use builder pattern to create and configure one.

```java
SonoranWsClient ws = SonoranWsClient.builder()
    .apiKey("your-api-key")
    .communityId("your-community-id")
    .url(SonoranURL.WS_HUB_DEV)
    .build();


ws.connect();
```

Base url is prefix of each request. SonoranURL provides default ones:

```java
public enum SonoranURL {
    WS_HUB_PROD("https://api.sonorancad.com/apiWsHub"),
    WS_HUB_DEV("https://staging-api.dev.sonorancad.com/apiWsHub");
}
```

## Listening to PUSH EVENTS

Listening to push events is really simple.

Just call SonoranWsClient#on method with following parameters:

- **Event Class** - Event class to listen for
- **Consumer** - Consumer to run after event has sent

```java
ws.on(CommunityLinkVerifiedEvent.class, event -> {
    String communityUserId = event.communityUserId();
    String accountUuid = event.accountUuid();
});
```

Each event class can have exactly one listener.

## Sending push events

Not done yet.

## Your own events

Its possible that events you may need are not implemented by default.

You can create your own event:

```java
public record CommunityLinkVerifiedEvent(
        String communityUserId,
        UUID accountUuid
) implements WSEvent {
}
```

Then you must register it in registry:

```java
ws.getRegistry()
    .register("EVENT_COMMUNITY_LINK_VERIFIED", CommunityLinkVerifiedEvent.class);
```

Then register listener as normal.