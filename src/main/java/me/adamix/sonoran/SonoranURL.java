package me.adamix.sonoran;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public enum SonoranURL {
    CAD_PROD("https://api.sonorancad.com/v2"),
    CAD_DEV("https://staging-api.dev.sonorancad.com/v2");

    @Getter
    private final @NotNull String url;

    SonoranURL(@NotNull String url) {
        this.url = url;
    }
}
