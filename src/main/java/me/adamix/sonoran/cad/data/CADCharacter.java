package me.adamix.sonoran.cad.data;

import java.util.List;

public record CADCharacter(
        int recordTypeId,
        int id,
        String syncId,
        String name,
        int type,
        List<CADCharacterSection> sections
) {

}
