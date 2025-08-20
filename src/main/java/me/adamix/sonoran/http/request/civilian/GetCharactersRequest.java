package me.adamix.sonoran.http.request.civilian;

import me.adamix.sonoran.cad.SonoranCad;
import me.adamix.sonoran.http.request.SonoranRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface GetCharactersRequest extends SonoranRequest {
	static @NotNull GetCharactersRequest byCadAccountUuid(@NotNull UUID cadAccountUuid) {
		return new ByAccountUuid(cadAccountUuid);
	}

	static @NotNull GetCharactersRequest byApiId(@NotNull String apiId) {
		return new ByApiId(apiId);
	}

	@NotNull Map<String, Object> getData();

	@Override
	default @NotNull String url() {
		return SonoranCad.API_URL + "civilian/get_characters";
	}

	@Override
	default @NotNull String type() {
		return "GET_CHARACTERS";
	}

	@Override
	default List<Object> data() {
		return List.of(getData());
	}

	record ByAccountUuid(@NotNull UUID cadAccountUuid) implements GetCharactersRequest {

		@Override
		public @NotNull Map<String, Object> getData() {
			return Map.of("account", cadAccountUuid);
		}
	}

	record ByApiId(@NotNull String apiId) implements GetCharactersRequest {

		@Override
		public @NotNull Map<String, Object> getData() {
			return Map.of("apiId", apiId);
		}
	}
}