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

	/**
	 * Queues a request to be sent asynchronously.
	 *
	 * @param request the request to send.
	 * @param consumer the callback to handle the response after the request is sent.
	 */
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

	/**
	 * Creates and queues a request to fetch an account by username.
	 * @param username the username to fetch the account for
	 * @return a {@link Result} that will be completed when the response is received
	 */
	public @NotNull Result<CadAccount> getAccount(@NotNull String username) {
		Result<CadAccount> result = new Result<>();

		sendRequest(new GetAccountRequest(username), response -> {
			result.completeFromJsonResponse(response, CadAccount.CODEC::decode);
		});

		return result;
	}

	/**
	 * Creates and queues a request to fetch current version of sonoran CAD.
	 * @return a {@link Result} that will be completed when the response is received
	 */
	public @NotNull Result<String> getVersion() {
		Result<String> result = new Result<>();

		sendRequest(new GetVersionRequest(), response -> {
			result.completeFromStringResponse(response, string -> string);
		});

		return result;
	}

	/**
	 * Creates and queues a request to fetch all servers of the current community.
	 * @return a {@link Result} that will be completed when the response is received.
	 */
	public @NotNull Result<List<CadServer>> getServers() {
		Result<List<CadServer>> result = new Result<>();

		sendRequest(new GetServersRequest(), response -> {
			result.completeFromJsonResponse(response, json -> {

				JsonObject jsonObject = json.getAsJsonObject();
				JsonArray servers = jsonObject.get("servers").getAsJsonArray();
				List<CadServer> list = new ArrayList<>();
				for (JsonElement server : servers) {
					CadServer parsed = CadServer.CODEC.decode(server);
					list.add(parsed);
				}

				return list;
			});
		});

		return result;
	}

	/**
	 * Creates and queues a request to fetch all characters of specified account.
	 * @param accountUuid the UUID of the account to fetch characters for.
	 * @return a {@link Result} that will be completed when the response is received.
	 */
	public @NotNull Result<List<CadCharacter>> getCharacters(@NotNull UUID accountUuid) {
		Result<List<CadCharacter>> result = new Result<>();

		sendRequest(new GetCharactersRequest(accountUuid), response -> {
			result.completeFromJsonResponse(response, json -> {
				JsonArray array = json.getAsJsonArray();

				List<CadCharacter> characterList = new ArrayList<>();

				for (JsonElement characterObject : array) {
					characterList.add(CadCharacter.CODEC.decode(characterObject));
				}

				return characterList;
			});
		});

		return result;
	}
}
