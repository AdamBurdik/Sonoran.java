[![](https://www.jitpack.io/v/adamBurdik/Sonoran.java.svg)](https://www.jitpack.io/#adamBurdik/Sonoran.java)

# Sonoran.java
Sonoran.java is a library that allows you to interact with the [Sonoran CAD](https://sonorancad.com/) API.

# v2
v2 branch is rewrite of library logic and implementation of [sonoran v2 api](https://docs.sonoransoftware.com/cad/api-integration/api-endpoints-v2)

New api provides actual http methods and rate limit info. So it should be used instead the legacy one.

> [!NOTE]
> This library is a work in progress. Many endpoints are not implemented yet.  
> I implemented just few required by my project.  

# Usage

This library implements HTTP and Web socket API for sonoran CAD

Check out the separate docs:
- [Http Client API](./docs/cad-http-client.md)
- [WS Client API](./docs/cad-ws-client.md)

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
[sonoran.js](https://github.com/Sonoran-Software/Sonoran.js) - Initial idea to create a library for java

[alpine](https://github.com/mudkipdev/alpine) - Awesome codec library