package me.adamix.sonoran.http.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PatchMethod {
    @NotNull String url();

    int rateLimit();

    // Array is expected to have alternating key, value
    // e.g.  {"key1", "value1", "key2", "value2"}
    @NotNull String[] headers() default {};
}
