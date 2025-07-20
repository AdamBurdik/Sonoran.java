package me.adamix.sonoran.codec;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Function;

public interface Codec<T> {
	Codec<String> STRING = CodecImpl.STRING;
	Codec<Integer> INT = CodecImpl.INT;
	Codec<Double> DOUBLE = CodecImpl.DOUBLE;
	Codec<Float> FLOAT = CodecImpl.FLOAT;
	Codec<Long> LONG = CodecImpl.LONG;
	Codec<Short> SHORT = CodecImpl.SHORT;
	Codec<Byte> BYTE = CodecImpl.BYTE;
	Codec<Boolean> BOOLEAN = CodecImpl.BOOLEAN;
	Codec<Character> CHAR = CodecImpl.CHAR;

	Codec<UUID> UUID = CodecImpl.UUID;

	JsonElement encode(T value);
	T decode(JsonElement json);

	default @NotNull Codec<T> optional() {
		return new CodecImpl.OptionalCodec<>(this, null);
	}

	default @NotNull Codec<T> optional(@NotNull T defaultValue) {
		return new CodecImpl.OptionalCodec<>(this, defaultValue);
	}
}
