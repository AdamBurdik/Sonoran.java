package me.adamix.sonoran.cad;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.adamix.sonoran.Sonoran;
import me.adamix.sonoran.cad.data.CadCharacter;
import me.adamix.sonoran.cad.data.general.CadAccount;
import me.adamix.sonoran.cad.data.result.Result;
import me.adamix.sonoran.cad.data.general.CadServer;
import me.adamix.sonoran.http.handler.response.Response;
import me.adamix.sonoran.http.payload.JsonPayload;
import me.adamix.sonoran.http.request.SonoranRequest;
import me.adamix.sonoran.http.request.civilian.GetCharactersRequest;
import me.adamix.sonoran.http.request.general.GetAccountRequest;
import me.adamix.sonoran.http.request.general.GetServersRequest;
import me.adamix.sonoran.http.request.general.GetVersionRequest;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class SonoranCad {
	private static final Gson gson = new Gson();
	private final @NotNull Sonoran sonoran;
	private final @NotNull String apiToken;
	private final @NotNull String communityId;

	public SonoranCad(@NotNull Sonoran sonoran, @NotNull String apiToken, @NotNull String communityId) {
		this.sonoran = sonoran;
		this.apiToken = apiToken;
		this.communityId = communityId;
	}

	@ApiStatus.Internal
	public void sendRequest(
			@NotNull SonoranRequest request,
			@NotNull Consumer<Response> consumer
	) {
		JsonObject payload = new JsonObject();
		payload.addProperty("key", apiToken);
		payload.addProperty("id", communityId);
		payload.addProperty("type", request.type());

		JsonArray dataArray = new JsonArray();
		for (Object object : request.data()) {
			dataArray.add(gson.toJsonTree(object));
		}
		payload.add("data", dataArray);

		sonoran.requestHandler().scheduleRequest(
				request.url(),
				new JsonPayload(payload),
				Map.of("Content-Type", "application/json"),
				consumer
		);
	}

	public @NotNull Result<CadAccount> getAccount(@NotNull String username) {
		Result<CadAccount> result = new Result<>();

		sendRequest(new GetAccountRequest(username), response -> {
			result.completeFromJsonResponse(response, json -> CadAccount.parse(json.getAsJsonObject()));
		});

		return result;
	}

	public @NotNull Result<String> getVersion() {
		Result<String> result = new Result<>();

		sendRequest(new GetVersionRequest(), response -> {
			result.completeFromStringResponse(response, string -> string);
		});

		return result;
	}

	public @NotNull Result<List<CadServer>> getServers() {
		Result<List<CadServer>> result = new Result<>();

		sendRequest(new GetServersRequest(), response -> {
			result.completeFromJsonResponse(response, json -> {

				JsonObject jsonObject = json.getAsJsonObject();
				JsonArray servers = jsonObject.get("servers").getAsJsonArray();
				List<CadServer> list = new ArrayList<>();
				for (JsonElement server : servers) {
					CadServer parsed = CadServer.parse(server.getAsJsonObject());
					list.add(parsed);
				}

				return list;
			});
		});

		return result;
	}

	public @NotNull Result<List<CadCharacter>> getCharacters(@NotNull UUID acountUuid) {
		Result<List<CadCharacter>> result = new Result<>();

		sendRequest(new GetCharactersRequest(acountUuid), response -> {
			result.completeFromJsonResponse(response, json -> {
				JsonArray array = json.getAsJsonArray();

				List<CadCharacter> characterList = new ArrayList<>();

				for (JsonElement characterObject : array) {
					characterList.add(CadCharacter.parse(characterObject.getAsJsonObject()));
				}

				return characterList;
			});
		});

		return result;
	}
}
