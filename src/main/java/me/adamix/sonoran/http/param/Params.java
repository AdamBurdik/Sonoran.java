package me.adamix.sonoran.http.param;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Params {
    private static final Gson gson = new Gson();

    private final Map<String, Object> map = new HashMap<>();

    public boolean hasParam(@NotNull String key) {
        return map.containsKey(key);
    }

    public @Nullable Object getParam(@NotNull String key) {
        return map.get(key);
    }

    public String resolveUrl(String url) {
        String resolved = url;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            resolved = resolved.replace("{" + entry.getKey() + "}", String.valueOf(entry.getValue()));
        }
        return resolved;
    }

    public String toJson() {
        return gson.toJson(map);
    }

    public String toQueryString() {
        if (map.isEmpty()) return "";
        StringJoiner joiner = new StringJoiner("&", "?", "");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            joiner.add(
                    URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8)
                            + "="
                            + URLEncoder.encode(String.valueOf(entry.getValue()), StandardCharsets.UTF_8)
            );
        }
        return joiner.toString();
    }

    public static Params of(String key, Object value) {
        Params params = new Params();
        params.map.put(key, value);
        return params;
    }

    public static Params of(String k1, Object v1, String k2, Object v2) {
        Params params = of(k1, v1);
        params.map.put(k2, v2);
        return params;
    }

    public static Params of(
            String k1, Object v1,
            String k2, Object v2,
            String k3, Object v3
    ) {
        Params params = of(k1, v1, k2, v2);
        params.map.put(k3, v3);
        return params;
    }

    public static Params of(
            String k1, Object v1,
            String k2, Object v2,
            String k3, Object v3,
            String k4, Object v4
    ) {
        Params params = of(k1, v1, k2, v2, k3, v3);
        params.map.put(k4, v4);
        return params;
    }

    public static Params empty() {
        return new Params();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }
}