package me.adamix.sonoran.http.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Params.class)
public @interface Param {
    @NotNull String key();
    @NotNull Class<?> type();
    boolean required() default true;
}
