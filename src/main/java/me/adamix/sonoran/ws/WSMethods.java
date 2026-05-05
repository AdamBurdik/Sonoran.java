package me.adamix.sonoran.ws;

public enum WSMethods {
    AUTHENTICATE_V2("authenticatev2"),

    PUSH_EVENTS("pushEvents");

    private final String method;

    WSMethods(String method) {
        this.method = method;
    }

    public String method() {
        return method;
    }
}
