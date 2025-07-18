package me.adamix.sonoran.http.request.general;

import me.adamix.sonoran.http.request.SonoranRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GetVersionRequest implements SonoranRequest {
	@Override
	public @NotNull String url() {
		return "https://api.sonorancad.com/general/get_version";
	}

	@Override
	public @NotNull String type() {
		return "GET_VERSION";
	}

	@Override
	public List<Object> data() {
		return List.of();
	}
}
