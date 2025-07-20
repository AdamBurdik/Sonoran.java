package me.adamix.sonoran.http.request.general;

import me.adamix.sonoran.http.request.SonoranRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public record GetAccountByUsernameRequest(@NotNull String username) implements SonoranRequest {
	@Override
	public @NotNull String url() {
		return "https://api.sonorancad.com/general/get_account";
	}

	@Override
	public @NotNull String type() {
		return "GET_ACCOUNT";
	}

	@Override
	public List<Object> data() {
		return List.of(Map.of("username", username));
	}
}
