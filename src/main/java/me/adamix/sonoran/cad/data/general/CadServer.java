package me.adamix.sonoran.cad.data.general;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record CadServer(
		int id,
		@NotNull String name,
		@NotNull String description,
		@Nullable String signal,
		@NotNull String mapUrl,
		@Nullable String mapIp,
		@Nullable String mapPort,
		boolean differingOutbound,
		@Nullable String outboundIp,
		@Nullable String listenerPort,
		boolean enableMap,
		@Nullable String mapType,
		boolean isStatic
) {
	public static @NotNull CadServer parse(@NotNull com.google.gson.JsonObject json) {
		return new CadServer(
				json.get("id").getAsInt(),
				json.get("name").getAsString(),
				json.get("description").getAsString(),
				json.has("signal") && !json.get("signal").isJsonNull() ? json.get("signal").getAsString() : null,
				json.get("mapUrl").getAsString(),
				json.has("mapIp") && !json.get("mapIp").isJsonNull() ? json.get("mapIp").getAsString() : null,
				json.has("mapPort") && !json.get("mapPort").isJsonNull() ? json.get("mapPort").getAsString() : null,
				json.get("differingOutbound").getAsBoolean(),
				json.has("outboundIp") && !json.get("outboundIp").isJsonNull() ? json.get("outboundIp").getAsString() : null,
				json.has("listenerPort") && !json.get("listenerPort").isJsonNull() ? json.get("listenerPort").getAsString() : null,
				json.get("enableMap").getAsBoolean(),
				json.has("mapType") && !json.get("mapType").isJsonNull() ? json.get("mapType").getAsString() : null,
				json.get("isStatic").getAsBoolean()
		);
	}

}