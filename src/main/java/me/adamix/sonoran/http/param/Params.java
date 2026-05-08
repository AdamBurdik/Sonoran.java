package me.adamix.sonoran.http.param;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        List<String> usedKeys = null;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            if (!resolved.contains(placeholder)) {
                continue;
            }
            if (usedKeys == null) {
                usedKeys = new ArrayList<>();
            }
            usedKeys.add(entry.getKey());
            resolved = resolved.replace(placeholder, encodePathValue(entry.getValue()));
        }
        if (usedKeys != null) {
            for (String key : usedKeys) {
                map.remove(key);
            }
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
            if (entry.getValue() == null) continue;
            joiner.add(
                    URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8)
                            + "="
                            + URLEncoder.encode(String.valueOf(entry.getValue()), StandardCharsets.UTF_8)
            );
        }
        return joiner.toString();
    }

    public @NotNull Params add(@NotNull String key, Object value) {
        map.put(key, value);
        return this;
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

    public static Params of(
            String k1, Object v1,
            String k2, Object v2,
            String k3, Object v3,
            String k4, Object v4,
            String k5, Object v5
    ) {
        Params params = of(k1, v1, k2, v2, k3, v3, k4, v4);
        params.map.put(k5, v5);
        return params;
    }

    public static Params of(
            String k1, Object v1,
            String k2, Object v2,
            String k3, Object v3,
            String k4, Object v4,
            String k5, Object v5,
            String k6, Object v6
    ) {
        Params params = of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
        params.map.put(k6, v6);
        return params;
    }

    public static Params empty() {
        return new Params();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    private static String encodePathValue(Object value) {
        if (value == null) return "";
        return URLEncoder.encode(String.valueOf(value), StandardCharsets.UTF_8)
                .replace("+", "%20");
    }
}
