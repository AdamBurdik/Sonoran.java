[![](https://www.jitpack.io/v/adamBurdik/Sonoran.java.svg)](https://www.jitpack.io/#adamBurdik/Sonoran.java)

# Sonoran.java
Sonoran.java is a library that allows you to interact with the [Sonoran CAD](https://sonorancad.com/) API.

# v2
v2 branch is rewrite of library logic and implementation of [sonoran v2 api](https://docs.sonoransoftware.com/cad/api-integration/api-endpoints-v2)

New api provides actual http methods and rate limit info. So it should be used instead the legacy one.

> [!INFO]
> This library is a work in progress — many endpoints are not yet implemented.  
> I plan to support all major Sonoran CAD API endpoints in future updates. 

# Usage

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

This library is using [alpine codecs](https://github.com/mudkipdev/alpine) for deserialization.

GetVersion response is this:
```json
{
  "version": 2,
  "name": "PLUS"
}
```

You can create record with static codec:

```java
public record GetVersionResponse(
    int version,
    @NotNull String name
) {
    public static final Codec<GetVersionResponse> CODEC = Codec.<GetVersionResponse>builder()
            .with("version", Codec.INTEGER, GetVersionResponse::version)
            .with("name", Codec.STRING, GetVersionResponse::name)
            .build(GetVersionResponse::new);

}
```

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
        GetVersionResponse.CODEC
);
```

# Requirements
- Java 25+ is required

# How To Build

1. Clone the repository
```bash
   git clone --branch v2 --single-branch git@github.com:AdamBurdik/Sonoran.java.git
```
2. Navigate to the directory
```bash
   cd Sonoran.java
```
3. Build the module
```bash
   gradlew build
```

# Dependency
You can add Sonoran.java as a dependency to your project, using jitpack
```kotlin
maven { url = uri("https://www.jitpack.io") }

dependencies {
    // Newest v2 commit
    implementation("com.github.adamBurdik:Sonoran.java:v2-SNAPSHOT")
}
```

# Contributing
Contributions are welcome!

If you have suggestions, ideas, or issues, feel free to open an issue or contact me on discord: @adamix.dev.

# Credits
[Sonoran CAD Docs](https://docs.sonoransoftware.com/cad/api-integration/) - Documentation for Sonoran CAD API

[sonoran.js](https://github.com/Sonoran-Software/Sonoran.js) - Initial idea to create a library for java

[alpine](https://github.com/mudkipdev/alpine) - Awesome codec library