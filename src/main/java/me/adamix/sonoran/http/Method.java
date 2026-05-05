package me.adamix.sonoran.http;

import lombok.Getter;

public enum Method {

    GET(true),
    HEAD(true),
    POST(false),
    PUT(false),
    DELETE(false),
    CONNECT(false),
    TRACE(true),
    OPTIONS(true),
    PATCH(false);

    @Getter
    private final boolean safe;

    Method(boolean safe) {
        this.safe = safe;
    }
}
