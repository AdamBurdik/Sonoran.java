package me.adamix.sonoran.http.request.general;

import me.adamix.sonoran.http.request.SonoranRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GetServersRequest implements SonoranRequest {
	@Override
	public @NotNull String url() {
		return "https://api.sonorancad.com/general/get_servers";
	}

	@Override
	public @NotNull String type() {
		return "GET_SERVERS";
	}

	@Override
	public List<Object> data() {
		return List.of();
	}
}
