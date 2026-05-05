package me.adamix.sonoran;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public enum SonoranURL {
    CAD_PROD("https://api.sonorancad.com/v2"),
    CAD_DEV("https://staging-api.dev.sonorancad.com/v2"),

    WS_HUB_PROD("https://api.sonorancad.com/apiWsHub"),
    WS_HUB_DEV("https://staging-api.dev.sonorancad.com/apiWsHub");

    @Getter
    private final @NotNull String url;

    SonoranURL(@NotNull String url) {
        this.url = url;
    }
}
