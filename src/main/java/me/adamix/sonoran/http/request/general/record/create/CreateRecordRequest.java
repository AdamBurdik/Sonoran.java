package me.adamix.sonoran.http.request.general.record.create;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import me.adamix.sonoran.http.request.SonoranRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CreateRecordRequest extends SonoranRequest {
    @Override
    default List<Object> data() {
        Map<String, Object> map = new HashMap<>();

        map.put("user", user());
        map.put("recordTypeId", templateId());
        map.put("useDictionary", useDictionary());
        map.put("replaceValues", replaceValues());

        if (json() != null) map.put("record", json());
        if (deleteAfterMinutes() != null) map.put("deleteAfterMinutes", deleteAfterMinutes());
        
        return List.of(Map.copyOf(map));

    }

    @NotNull String user();

    int templateId();

    boolean useDictionary();

    @Nullable Map<String, Object> replaceValues();

    @Nullable JsonElement json();

    @Nullable Long deleteAfterMinutes();
}
