package me.adamix.sonoran.cad.data;

import alpine.json.codec.Codec;

import java.util.List;

public record CADCharacter(int recordTypeId, int id, String syncId, String name, int type, List<CADCharacterSection> sections) {
    public static final Codec<CADCharacter> CODEC = Codec.<CADCharacter>builder()
            .with("recordTypeId", Codec.INTEGER, CADCharacter::recordTypeId)
            .with("id", Codec.INTEGER, CADCharacter::id)
            .with("syncId", Codec.STRING, CADCharacter::syncId)
            .with("name", Codec.STRING, CADCharacter::name)
            .with("type", Codec.INTEGER, CADCharacter::type)
            .with("sections", CADCharacterSection.CODEC.list(), CADCharacter::sections)
            .build(CADCharacter::new);
}
