package me.adamix.sonoran.http.request.general.record;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public interface GenericRecordProvider {
    static @NotNull GenericRecordProvider create(@NotNull String url, @NotNull String type) {
        return new GenericRecordProvider() {
            @Override
            public @NotNull GenericRecordRequest byApiId(
                    @NotNull String apiId,
                    int templateId,
                    boolean useDictionary,
                    long recordId,
                    Map<String, Object> replaceValues,
                    @Nullable JsonElement json
            ) {
                return new ByApiId(url, type, apiId, templateId, useDictionary, recordId, replaceValues, json);
            }

            @Override
            public @NotNull GenericRecordRequest byAccountUuid(
                    @NotNull UUID cadAccountUuid,
                    int templateId,
                    boolean useDictionary,
                    long recordId,
                    Map<String, Object> replaceValues,
                    @Nullable JsonElement json
            ) {
                return new ByAccountUuid(url, type, cadAccountUuid, templateId, useDictionary, recordId, replaceValues, json);
            }
        };
    }

    @NotNull GenericRecordRequest byApiId(
            @NotNull String apiId,
            int templateId,
            boolean useDictionary,
            long recordId,
            Map<String, Object> replaceValues,
            @Nullable JsonElement json
    );

    @NotNull GenericRecordRequest byAccountUuid(
            @NotNull UUID cadAccountUuid,
            int templateId,
            boolean useDictionary,
            long recordId,
            Map<String, Object> replaceValues,
            @Nullable JsonElement json
    );

    record ByAccountUuid(
            @NotNull String url,
            @NotNull String type,
            @NotNull UUID cadAccountUuid,
            int templateId,
            boolean useDictionary,
            long recordId,
            Map<String, Object> replaceValues,
            @Nullable JsonElement json
    ) implements GenericRecordRequest {
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
            long recordId,
            Map<String, Object> replaceValues,
            @Nullable JsonElement json
    ) implements GenericRecordRequest {
        @Override
        public @NotNull String user() {
            return apiId;
        }
    }
}
