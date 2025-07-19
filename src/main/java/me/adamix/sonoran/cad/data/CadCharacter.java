package me.adamix.sonoran.cad.data;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public record CadCharacter(
		long id,
		@NotNull String name
) {
	public static @NotNull CadCharacter parse(@NotNull JsonObject json) {
		// ToDO Implement parsing logic for everything    :((
		long id = json.get("id").getAsLong();
		String name = json.get("name").getAsString();

		return new CadCharacter(id, name);
	}
}
