# Sonoran.java
Sonoran.java is a library that allows you to interact with the [Sonoran CAD](https://sonorancad.com/) API.

# Disclaimer
This library is a work in progress — many endpoints are not yet implemented.  
I plan to support all major Sonoran CAD API endpoints in future updates.

# How To Build
1. Clone the repository
```bash
   git clone https://github.com/adamBurdik/Sonoran.java
```
2. Navigate to the directory
```bash
   cd Sonoran.java
```
3. Build the module
```bash
   gradlew build
```

# How To Use
1. Add Sonoran.java as a dependency to your project, using jitpack
```kotlin
maven { url = uri("https://www.jitpack.io") }

dependencies {
    implementation("com.github.adamBurdik:Sonoran.java:v1.0.3")
}
```
2. Create Sonoran instance with cad. 
```java
Sonoran sonoran = Sonoran.builder()
        .withCad("CAD_API_TOKEN_HERE", "CAD_COMMUNITY_ID_HERE")
        .build();
```
3. Use provided methods
```java
// Prints current sonoran version
sonoran.cad().getVersion((result) -> {
    switch (result) {
        case Result.Success<String> success -> System.out.println("Version: " + success.value());
        case Result.Error<String> error -> System.out.println(error.message());
        case Result.Exception<String> exception -> throw new RuntimeException(exception.exception());
        default -> throw new IllegalStateException("Unexpected value: " + result);
    }
});

// Retrieves CAD Account instance of provided username
sonoran.cad().getAccount("ACCOUNT_NAME_HERE", (result -> {
    switch (result) {
        case Result.Success<CadAccount> success -> System.out.println("Account: " + success.value());
        case Result.Error<CadAccount> error -> System.out.println(error.message());
        case Result.Exception<CadAccount> exception -> throw new RuntimeException(exception.exception());
        default -> throw new IllegalStateException("Unexpected value: " + result);
    }
}));
```

# Contributing
Contributions are welcome!

If you have suggestions, ideas, or issues, feel free to open an issue or contact me on discord: @adamix.dev.