package me.adamix.sonoran.http.annotation;

import me.adamix.sonoran.cad.request.civilian.GetCharactersRequest;
import me.adamix.sonoran.cad.request.general.GetAccountRequest;
import me.adamix.sonoran.cad.request.general.GetVersionRequest;
import me.adamix.sonoran.http.Method;
import me.adamix.sonoran.http.SonoranRequest;
import me.adamix.sonoran.http.param.ParamDefinition;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Methods {
    public static final SonoranRequest
            GET_VERSION = create(GetVersionRequest.class),
            GET_CHARACTERS = createGet(GetCharactersRequest.class),
            GET_ACCOUNT = createGet(GetAccountRequest.class);


    @ApiStatus.Internal
    public static SonoranRequest create(@NotNull Class<?> clazz) {
        if (clazz.isAnnotationPresent(GetMethod.class)) {
            return createGet(clazz);
        } else if (clazz.isAnnotationPresent(PostMethod.class)) {
            return createPost(clazz);
        } else {
            throw new IllegalStateException("Request class must be annotated by @GetMethod or @GetMethod");
        }
    }

    @ApiStatus.Internal
    public static SonoranRequest createGet(@NotNull Class<?> clazz) {
        GetMethod annotation = clazz.getAnnotation(GetMethod.class);

        return sonoranRequest(clazz, annotation.url(), annotation.headers(), annotation.rateLimit());
    }

    @ApiStatus.Internal
    public static @NonNull SonoranRequest sonoranRequest(
            @NotNull Class<?> clazz,
            String url,
            String[] headerArray,
            int rateLimit
    ) {
        Method method = Method.GET;

        Map<String, String> headers = new HashMap<>(headerArray.length / 2);

        for (int i = 0; i < headerArray.length; i += 2) {
            headers.put(headerArray[i], headerArray[i + 1]);
        }

        List<ParamDefinition> params = resolveParams(clazz);

        return new SonoranRequest(
                url,
                method,
                headers,
                rateLimit,
                params
        );
    }

    private static SonoranRequest createPost(@NotNull Class<?> clazz) {
        PostMethod annotation = clazz.getAnnotation(PostMethod.class);

        return sonoranRequest(clazz, annotation.url(), annotation.headers(), annotation.rateLimit());
    }

    private static @NotNull List<ParamDefinition> resolveParams(@NotNull Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Params.class)) {
            return Collections.emptyList();
        }

        Params params = clazz.getAnnotation(Params.class);

        return Arrays.stream(params.value())
                .map(p -> new ParamDefinition(
                        p.key(),
                        p.type(),
                        p.required())
                )
                .toList();
    }
}
