# Sonoran.java
Sonoran.java is a library that allows you to interact with the [Sonoran CAD](https://sonorancad.com/) API.

# Disclaimer
This library is a work in progress — many endpoints are not yet implemented.  
I plan to support all major Sonoran CAD API endpoints in future updates.

# Run the Example
This repository includes simple usage examples located in the `examples` folder.  
You can run them using the following Gradle task:
```bash
   ./gradlew runExample --args="<cad_api_token> <cad_community_id> <account_username>"
```
- Replace the arguments with your own valid values.
- The example will fetch and print the CAD account object for the given username.

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

# Contributing
Contributions are welcome!

If you have suggestions, ideas, or issues, feel free to open an issue or contact me on discord: @adamix.dev.