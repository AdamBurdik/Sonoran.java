package me.adamix.sonoran.cad.data;

import alpine.json.codec.Codec;

public record CADCharacterField(String label, String value, String uid) {
    public static final Codec<CADCharacterField> CODEC = Codec.<CADCharacterField>builder()
            .with("label", Codec.STRING, CADCharacterField::label)
            .with("value", Codec.STRING, CADCharacterField::value)
            .with("uid", Codec.STRING, CADCharacterField::uid)
            .build(CADCharacterField::new);
}
