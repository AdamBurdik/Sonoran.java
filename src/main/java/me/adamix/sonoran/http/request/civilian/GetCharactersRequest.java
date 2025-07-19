package me.adamix.sonoran.http.request.civilian;

import me.adamix.sonoran.http.request.SonoranRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record GetCharactersRequest(@NotNull UUID cadAccountUuid) implements SonoranRequest {
	@Override
	public @NotNull String url() {
		return "https://api.sonorancad.com/civilian/get_characters";
	}

	@Override
	public @NotNull String type() {
		return "GET_CHARACTERS";
	}

	@Override
	public List<Object> data() {
		return List.of(Map.of("account", cadAccountUuid));
	}
}
