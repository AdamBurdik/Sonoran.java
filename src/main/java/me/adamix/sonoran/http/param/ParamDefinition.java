package me.adamix.sonoran.http.param;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParamDefinition {
    private final String key;
    private final Class<?> type;
    private boolean required;
}