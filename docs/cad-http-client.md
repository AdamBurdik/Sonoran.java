# CAD HTTP Client

Docs: [Sonoran HTTP Docs](https://docs.sonoransoftware.com/cad/api-integration/api-endpoints-v2)

---

## Setup
To interact with the API, SonoranCadClient is needed.
You can use builder pattern to create and configure one.

```java
SonoranCadClient cad = SonoranCadClient.builder()
    .apiKey("your-api-key")
    .communityId("your-community-id")
    .baseUrl(SonoranURL.CAD_DEV)
    .build();
```
Base url is prefix of each request. SonoranURL provides default ones:
```java
public enum SonoranURL {
    CAD_PROD("https://api.sonorancad.com/v2"),
    CAD_DEV("https://staging-api.dev.sonorancad.com/v2");
}
```

## Calling endpoints
Calling individual endpoints is really easy. Just call method on the client instance.

All response are wrapped in CompletableFuture with virtual thread executor.

```java
CompletableFuture<GetVersionResponse> response = cad.getVersion();
```

## Your own requests
Not all the endpoints are implemented in this library.

You can create your own pretty easily tho.

Lets recreate getVersion endpoint

### 1. First you need request class

```java
@GetMethod(url = "/general/version", rateLimit = 10)
public interface GetVersionRequest {
}
```

- **@GetMethod** - annotation tells the client to use GET http method
- ***url** - Is endpoint location from the base. *Must start with /*
- ***rateLimit** - Maximum amount of this request per minute.
- **headers** - Send additional headers with this request. <br> This is array with alternating key, value <br> example: {"key1", "value1", "key2", "value2"}

*required values

### 2. You may also need response data

Response data is just simple record.

GetVersion response is this:
```json
{
  "version": 2,
  "name": "PLUS"
}
```

You can create record:

```java
public record GetVersionResponse(
    int version,
    @NotNull String name
) {
}
```

Internally GSON is used for deserialization.

For more complex data you may need to register your own type adapters.

You can supply your own GSON instance to builder. 

For built-in type adapters see [TypeAdapters.java](../src/main/java/me/adamix/sonoran/json/TypeAdapters.java).

### 3. Parsing annotation
Request annotation needs to be parsed. By default its done using functions in Methods class.

These functions are publicly exposed for your usage.

For example, you can create your own method registry class:
```java
import me.adamix.sonoran.http.annotation.Methods;

class MyCustomMethods {
    public static final SonoranRequest
            GET_VERSION = Methods.create(GetVersionRequest.class);
}
```

### 4. Method handlers

Finally, you need to send the request.

SonoranCadClient provides method sendRequest, using which you can send your requests.

```java
SonoranCadClient cad = ...;

GetVersionRequest request = MyCustomMethods.GET_VERSION;

CompletableFuture<GetVersionRequest> response = cad.sendRequest(
        request,
        Params.empty(),
        new TypeToken<>() {}
);
```