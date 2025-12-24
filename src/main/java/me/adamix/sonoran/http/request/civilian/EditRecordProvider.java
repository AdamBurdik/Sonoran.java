package me.adamix.sonoran.http.request.civilian;

import com.google.gson.JsonElement;
import me.adamix.sonoran.cad.SonoranCad;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

// https://docs.sonoransoftware.com/cad/api-integration/api-endpoints/civilian/edit-character
public interface EditRecordProvider {
	@NotNull EditRecordRequest byApiId(
			@NotNull String apiId,
			int templateId,
			boolean useDictionary,
			long recordId,
			Map<String, Object> replaceValues,
			@Nullable JsonElement json
	);

	@NotNull EditRecordRequest byAccountUuid(
			@NotNull UUID cadAccountUuid,
			int templateId,
			boolean useDictionary,
			long recordId,
			Map<String, Object> replaceValues,
			@Nullable JsonElement json
	);

	EditRecordProvider CHARACTER = create(SonoranCad.API_URL + "civilian/edit_character", "EDIT_CHARACTER");
	EditRecordProvider CUSTOM_RECORD = create(SonoranCad.API_URL + "general/edit_record", "EDIT_RECORD");

	private static EditRecordProvider create(@NotNull String url, @NotNull String type) {
		return new EditRecordProvider() {

			@Override
			public @NotNull EditRecordRequest byApiId(
					@NotNull String apiId,
					int templateId,
					boolean useDictionary,
					long recordId,
					Map<String, Object> replaceValues,
					@Nullable JsonElement json
			) {
				return new ByApiId(url, type, apiId, templateId, useDictionary, recordId, replaceValues, json);
			}

			@Override
			public @NotNull EditRecordRequest byAccountUuid(
					@NotNull UUID cadAccountUuid,
					int templateId,
					boolean useDictionary,
					long recordId,
					Map<String, Object> replaceValues,
					@Nullable JsonElement json
			) {
				return new ByAccountUuid(url, type, cadAccountUuid, templateId, useDictionary, recordId, replaceValues, json);
			}
		};
	}

	record ByAccountUuid(
			@NotNull String url,
			@NotNull String type,
			@NotNull UUID cadAccountUuid,
			int templateId,
			boolean useDictionary,
			long recordId,
			Map<String, Object> replaceValues,
			@Nullable JsonElement json
	) implements EditRecordRequest {
		@Override
		public @NotNull String user() {
			return cadAccountUuid.toString();
		}
	}

	record ByApiId(
			@NotNull String url,
			@NotNull String type,
			@NotNull String apiId,
			int templateId,
			boolean useDictionary,
			long recordId,
			Map<String, Object> replaceValues,
			@Nullable JsonElement json
	) implements EditRecordRequest {
		@Override
		public @NotNull String user() {
			return apiId;
		}
	}
}
