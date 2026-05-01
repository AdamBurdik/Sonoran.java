package me.adamix.sonoran.cad.request.general;

import alpine.json.codec.Codec;
import org.jetbrains.annotations.NotNull;

public record GetVersionResponse(
        int version,
        @NotNull String name
) {
    public static final Codec<GetVersionResponse> CODEC = Codec.<GetVersionResponse>builder()
            .with("version", Codec.INTEGER, GetVersionResponse::version)
            .with("name", Codec.STRING, GetVersionResponse::name)
            .build(GetVersionResponse::new);

}
