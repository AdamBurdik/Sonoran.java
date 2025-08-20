package me.adamix.sonoran.http.request.civilian;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import me.adamix.sonoran.cad.SonoranCad;
import me.adamix.sonoran.http.request.SonoranRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

// https://docs.sonoransoftware.com/cad/api-integration/api-endpoints/civilian/edit-character
public interface EditCharacterRequest extends SonoranRequest {
	static @NotNull EditCharacterRequest byCadAccountUuid(
			@NotNull UUID cadAccountUuid,
			int templateId,
			boolean useDictionary,
			long recordId,
			@Nullable Map<String, Object> replaceValues,
			@Nullable JsonElement json
	) {
		return new EditCharacterRequest.ByAccountUuid(cadAccountUuid, templateId, useDictionary, recordId, replaceValues, json);
	}

	static @NotNull EditCharacterRequest byApiId(
			@NotNull String apiId,
			int templateId,
			boolean useDictionary,
			long recordId,
			@Nullable Map<String, Object> replaceValues,
			@Nullable JsonElement json
	) {
		return new EditCharacterRequest.ByApiId(apiId, templateId, useDictionary, recordId, replaceValues, json);
	}

	@Override
	default @NotNull String url() {
		return SonoranCad.API_URL + "civilian/edit_character";
	}

	@Override
	default @NotNull String type() {
		return "EDIT_CHARACTER";
	}

	@Override
	default List<Object> data() {
		return List.of(
				Map.of(
						"user", user(),
						"templateId", templateId(),
						"useDictionary", useDictionary(),
						"recordId", recordId(),
						"replaceValues", replaceValues() != null ? replaceValues() : JsonNull.INSTANCE.toString(),
						"record", json() != null ? json().toString() : JsonNull.INSTANCE.toString()
				)
		);
	}

	@NotNull String user();
	int templateId();
	boolean useDictionary();
	long recordId();
	@Nullable Map<String, Object> replaceValues();
	@Nullable JsonElement json();

	record ByAccountUuid(
			@NotNull UUID cadAccountUuid,
			int templateId,
			boolean useDictionary,
			long recordId,
			Map<String, Object> replaceValues,
			@Nullable JsonElement json
	)  implements EditCharacterRequest {

		@Override
		public @NotNull String user() {
			return cadAccountUuid.toString();
		}
	}

	record ByApiId(
			@NotNull String apiId,
			int templateId,
			boolean useDictionary,
			long recordId,
			Map<String, Object> replaceValues,
			@Nullable JsonElement json
	)  implements EditCharacterRequest {

		@Override
		public @NotNull String user() {
			return apiId;
		}
	}
}
