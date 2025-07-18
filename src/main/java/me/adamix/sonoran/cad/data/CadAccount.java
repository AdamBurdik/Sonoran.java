package me.adamix.sonoran.cad.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record CadAccount(
		@NotNull String username,
		@NotNull UUID uuid
) {
	public static @NotNull CadAccount parse(@NotNull JsonObject json) {
		return new CadAccount(
				json.get("username").getAsString(),
				UUID.fromString(json.get("uuid").getAsString())
		);
	}
}
