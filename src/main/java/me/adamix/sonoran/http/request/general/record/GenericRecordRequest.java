package me.adamix.sonoran.http.request.general.record;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import me.adamix.sonoran.http.request.SonoranRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public interface GenericRecordRequest extends SonoranRequest {
    @Override
    default List<Object> data() {
        return List.of(
                Map.of(
                        "user", user(),
                        "templateId", templateId(),
                        "useDictionary", useDictionary(),
                        "recordId", recordId(),
                        "replaceValues", replaceValues() != null ? replaceValues() : JsonNull.INSTANCE.toString(),
                        "record", json() != null ? json().toString() : JsonNull.INSTANCE.toString()
                )
        );
    }

    @NotNull String user();

    int templateId();

    boolean useDictionary();

    long recordId();

    @Nullable Map<String, Object> replaceValues();

    @Nullable JsonElement json();
}
