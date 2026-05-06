package me.adamix.sonoran.cad.data;

import java.util.List;

public record CADCharacterSection(
        int category,
        String label,
        List<CADCharacterField> fields
) {
}
