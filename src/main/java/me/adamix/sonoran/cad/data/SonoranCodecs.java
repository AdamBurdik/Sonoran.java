package me.adamix.sonoran.cad.data;

import alpine.json.codec.Codec;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class SonoranCodecs {
    public static final Codec<Instant> INSTANT = Codec.STRING.map(SonoranCodecs::parseInstant, Instant::toString);

    private static Instant parseInstant(String value) {
        try {
            return Instant.parse(value);
        } catch (RuntimeException ex) {
            LocalDateTime localDateTime = LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return localDateTime.toInstant(ZoneOffset.UTC);
        }
    }
}
