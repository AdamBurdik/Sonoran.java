package me.adamix.sonoran.http.request.general;

import me.adamix.sonoran.cad.SonoranCad;
import me.adamix.sonoran.http.request.SonoranRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public record GetAccountByAPIIdRequest(@NotNull String apiId) implements SonoranRequest {
	@Override
	public @NotNull String url() {
		return SonoranCad.API_URL + "general/get_account";
	}

	@Override
	public @NotNull String type() {
		return "GET_ACCOUNT";
	}

	@Override
	public List<Object> data() {
		return List.of(Map.of("apiId", apiId));
	}
}

