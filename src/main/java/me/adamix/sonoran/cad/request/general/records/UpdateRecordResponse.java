package me.adamix.sonoran.cad.request.general.records;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record UpdateRecordResponse(
        int recordTypeId,
        int id,
        @NotNull String name,
        int type,
        @NotNull List<Section> sections
) {
    public record Section(
            int category,
            @NotNull String label,
            @NotNull List<Field> fields
    ) {
    }

    public record Field(
            @NotNull String label,
            @NotNull String value,
            @NotNull String uid
    ) {
    }
}
