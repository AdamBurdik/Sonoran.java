package me.adamix.sonoran.cad.data.general;

import me.adamix.sonoran.codec.Codec;
import me.adamix.sonoran.codec.StructCodec;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record CadAccount(
        @NotNull String username,
        @NotNull UUID uuid
) {
    public static final Codec<CadAccount> CODEC = StructCodec.struct(
            "username", Codec.STRING, CadAccount::username,
            "uuid", Codec.UUID, CadAccount::uuid,
            CadAccount::new
    );
}
