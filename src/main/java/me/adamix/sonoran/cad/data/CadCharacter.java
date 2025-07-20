package me.adamix.sonoran.cad.data;

import me.adamix.sonoran.codec.Codec;
import me.adamix.sonoran.codec.StructCodec;
import org.jetbrains.annotations.NotNull;

public record CadCharacter(
		long recordTypeId,
		long id,
		@NotNull String name,
		long type
) {
	// ToDO Add fields for everything
	public static final Codec<CadCharacter> CODEC = StructCodec.struct(
			"recordTypeId", Codec.LONG, CadCharacter::recordTypeId,
			"id", Codec.LONG, CadCharacter::id,
			"name", Codec.STRING, CadCharacter::name,
			"type", Codec.LONG, CadCharacter::type,
			CadCharacter::new
	);
}
