package me.adamix.sonoran.http;

import lombok.Data;
import me.adamix.sonoran.http.param.ParamDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

@Data
public class SonoranRequest {
    private final String url;
    private final Method method;
    private final Map<String, String> headers;
    private final int rateLimit;
    private final List<ParamDefinition> params;

    public SonoranRequest(
            @NotNull String url,
            @NotNull Method method,
            @Nullable Map<String, String> headers,
            int rateLimit,
            @Nullable List<ParamDefinition> params
    ) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.rateLimit = rateLimit;
        this.params = params;
    }
}
