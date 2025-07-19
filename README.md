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
    implementation("com.github.adamBurdik:Sonoran.java:VERSION") // Get version from https://github.com/AdamBurdik/Sonoran.java/releases
}
```
2. Create Sonoran instance with cad. 
```java
Sonoran sonoran = Sonoran.builder()
        .withCad("CAD_API_TOKEN_HERE", "CAD_COMMUNITY_ID_HERE")
        .build();

// Shuts down the internal HTTP client and task executor.
// Call this method during application shutdown to clean up resources.
sonoran.shutdown();
```
3. Use provided methods
```java
// Prints the current Sonoran CAD version
sonoran.cad().getVersion()
    .onSuccess(version -> System.out.println("Version: " + version))
    .onError(error -> System.out.println("API error: " + error.message())) // Use onError to handle unsuccessful API responses
    .onException(Throwable::printStackTrace); // Use onException to handle unexpected internal errors

// Retrieves the CAD Account for the provided username
sonoran.cad().getAccount("ACCOUNT_NAME_HERE")
    .onSuccess(account -> System.out.println("Found account: " + account))
    .onError(error -> System.out.println("API error: " + error.message()))
    .onException(Throwable::printStackTrace);
```

# Contributing
Contributions are welcome!

If you have suggestions, ideas, or issues, feel free to open an issue or contact me on discord: @adamix.dev.