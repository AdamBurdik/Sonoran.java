package me.adamix.sonoran.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.UUID;

public final class TypeAdapters {

    public static class UUIDAdapter extends TypeAdapter<UUID> {
        public UUID read(JsonReader in) throws IOException {
            return UUID.fromString(in.nextString());
        }

        public void write(JsonWriter out, UUID value) throws IOException {
            out.value(value.toString());
        }
    }

    public static class InstantAdapter extends TypeAdapter<Instant> {
        private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .optionalStart().appendOffsetId().optionalEnd()
                .toFormatter()
                .withZone(ZoneOffset.UTC);

        public Instant read(JsonReader in) throws IOException {
            return Instant.from(FORMATTER.parse(in.nextString()));
        }

        public void write(JsonWriter out, Instant value) throws IOException {
            out.value(value.toString());
        }
    }

    // Handles any enum as ordinal — mirrors Codec.ordinal(...)
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static class OrdinalEnumAdapter extends TypeAdapter<Enum> {
        public Enum read(JsonReader in) throws IOException {
            // Can't do much without the type here — see note below
            throw new UnsupportedOperationException("Use OrdinalEnumAdapter.of(Class) instead");
        }

        public void write(JsonWriter out, Enum value) throws IOException {
            out.value(value.ordinal());
        }
    }

    // Per-enum adapter factory — use this for specific enums
    public static <E extends Enum<E>> TypeAdapter<E> ordinal(Class<E> clazz) {
        return new TypeAdapter<>() {
            public E read(JsonReader in) throws IOException {
                return clazz.getEnumConstants()[in.nextInt()];
            }

            public void write(JsonWriter out, E value) throws IOException {
                out.value(value.ordinal());
            }
        };
    }
}