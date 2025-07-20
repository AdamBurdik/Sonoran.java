package me.adamix.sonoran.cad.data.general;

import me.adamix.sonoran.codec.Codec;
import me.adamix.sonoran.codec.StructCodec;
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
	public static final Codec<CadServer> CODEC = StructCodec.struct(
			"id", Codec.INT, CadServer::id,
			"name", Codec.STRING, CadServer::name,
			"description", Codec.STRING, CadServer::description,
			"signal", Codec.STRING.optional(), CadServer::signal,
			"mapUrl", Codec.STRING, CadServer::mapUrl,
			"mapIp", Codec.STRING.optional(), CadServer::mapIp,
			"mapPort", Codec.STRING.optional(), CadServer::mapPort,
			"differingOutbound", Codec.BOOLEAN, CadServer::differingOutbound,
			"outboundIp", Codec.STRING.optional(), CadServer::outboundIp,
			"listenerPort", Codec.STRING.optional(), CadServer::listenerPort,
			"enableMap", Codec.BOOLEAN, CadServer::enableMap,
			"mapType", Codec.STRING.optional(), CadServer::mapType,
			"isStatic", Codec.BOOLEAN, CadServer::isStatic,
			CadServer::new
	);
}