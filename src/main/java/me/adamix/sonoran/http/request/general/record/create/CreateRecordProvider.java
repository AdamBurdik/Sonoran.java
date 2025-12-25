package me.adamix.sonoran.http.request.general.record.create;

import com.google.gson.JsonElement;
import me.adamix.sonoran.cad.SonoranCad;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public interface CreateRecordProvider {
    // https://docs.sonoransoftware.com/cad/api-integration/api-endpoints/civilian/new-character
    CreateRecordProvider CHARACTER = create(SonoranCad.API_URL + "civilian/new_character", "NEW_CHARACTER");

    // https://docs.sonoransoftware.com/cad/api-integration/api-endpoints/general/custom-records/new-record
    CreateRecordProvider CUSTOM_RECORD = create(SonoranCad.API_URL + "general/new_record", "NEW_RECORD");

    static @NotNull CreateRecordProvider create(@NotNull String url, @NotNull String type) {
        return new CreateRecordProvider() {
            @Override
            public @NotNull CreateRecordRequest byApiId(
                    @NotNull String apiId,
                    int templateId,
                    boolean useDictionary,
                    Map<String, Object> replaceValues,
                    @Nullable JsonElement json,
                    @Nullable Long deleteAfterMinutes
            ) {
                return new ByApiId(url, type, apiId, templateId, useDictionary, replaceValues, json, deleteAfterMinutes);
            }

            @Override
            public @NotNull CreateRecordRequest byAccountUuid(
                    @NotNull UUID cadAccountUuid,
                    int templateId,
                    boolean useDictionary,
                    Map<String, Object> replaceValues,
                    @Nullable JsonElement json,
                    @Nullable Long deleteAfterMinutes
            ) {
                return new ByAccountUuid(url, type, cadAccountUuid, templateId, useDictionary, replaceValues, json, deleteAfterMinutes);
            }
        };
    }

    @NotNull CreateRecordRequest byApiId(
            @NotNull String apiId,
            int templateId,
            boolean useDictionary,
            Map<String, Object> replaceValues,
            @Nullable JsonElement json,
            @Nullable Long deleteAfterMinutes
    );

    @NotNull CreateRecordRequest byAccountUuid(
            @NotNull UUID cadAccountUuid,
            int templateId,
            boolean useDictionary,
            Map<String, Object> replaceValues,
            @Nullable JsonElement json,
            @Nullable Long deleteAfterMinutes
    );

    record ByAccountUuid(
            @NotNull String url,
            @NotNull String type,
            @NotNull UUID cadAccountUuid,
            int templateId,
            boolean useDictionary,
            Map<String, Object> replaceValues,
            @Nullable JsonElement json,
            @Nullable Long deleteAfterMinutes
    ) implements CreateRecordRequest {
        @Override
        public @NotNull String user() {
            return cadAccountUuid.toString();
        }
    }

    record ByApiId(
            @NotNull String url,
            @NotNull String type,
            @NotNull String apiId,
            int templateId,
            boolean useDictionary,
            Map<String, Object> replaceValues,
            @Nullable JsonElement json,
            @Nullable Long deleteAfterMinutes
    ) implements CreateRecordRequest {
        @Override
        public @NotNull String user() {
            return apiId;
        }
    }
}
