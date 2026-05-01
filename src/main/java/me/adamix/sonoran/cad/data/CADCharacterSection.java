package me.adamix.sonoran.cad.data;

import alpine.json.codec.Codec;

import java.util.List;

public record CADCharacterSection(int category, String label, List<CADCharacterField> fields) {
    public static final Codec<CADCharacterSection> CODEC = Codec.<CADCharacterSection>builder()
            .with("category", Codec.INTEGER, CADCharacterSection::category)
            .with("label", Codec.STRING, CADCharacterSection::label)
            .with("fields", CADCharacterField.CODEC.list(), CADCharacterSection::fields)
            .build(CADCharacterSection::new);
}
