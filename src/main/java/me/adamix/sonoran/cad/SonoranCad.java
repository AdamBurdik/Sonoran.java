package me.adamix.sonoran.cad;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.adamix.sonoran.Sonoran;
import me.adamix.sonoran.cad.data.general.CadAccount;
import me.adamix.sonoran.cad.data.Result;
import me.adamix.sonoran.cad.data.general.CadServer;
import me.adamix.sonoran.http.handler.response.Response;
import me.adamix.sonoran.http.payload.JsonPayload;
import me.adamix.sonoran.http.request.SonoranRequest;
import me.adamix.sonoran.http.request.general.GetAccountRequest;
import me.adamix.sonoran.http.request.general.GetServersRequest;
import me.adamix.sonoran.http.request.general.GetVersionRequest;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

	private void sendRequest(
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

	public void getAccount(@NotNull String username, @NotNull Consumer<Result<CadAccount>> consumer) {
		sendRequest(new GetAccountRequest(username), (response) -> {
			if (response instanceof Response.Error(Exception exception)) {
				consumer.accept(new Result.Exception<>(exception));
			}
			else if (response instanceof Response.Success success) {
				if (success.statusCode() != 200) {
					consumer.accept(new Result.Error<>(success.statusCode(), success.body()));
				} else {
					consumer.accept(new Result.Success<>(CadAccount.parse(success.jsonBody()), success));
				}
			}
		});
	}

	public void getServers(@NotNull Consumer<Result<List<CadServer>>> consumer) {
		sendRequest(new GetServersRequest(), (response -> {
			if (response instanceof Response.Error(Exception exception)) {
				consumer.accept(new Result.Exception<>(exception));
			}
			else if (response instanceof Response.Success success) {
				if (success.statusCode() != 200) {
					consumer.accept(new Result.Error<>(success.statusCode(), success.body()));
				} else {
					JsonObject json = success.jsonBody();
					JsonArray servers = json.get("servers").getAsJsonArray();
					List<CadServer> list = new ArrayList<>();
					for (JsonElement server : servers) {
						CadServer parsed = CadServer.parse(server.getAsJsonObject());
						list.add(parsed);
					}

					consumer.accept(new Result.Success<>(list, success));
				}
			}
		}));
	}

	public void getVersion(@NotNull Consumer<Result<String>> consumer) {
		sendRequest(new GetVersionRequest(), (response -> {
			if (response instanceof Response.Error(Exception exception)) {
				consumer.accept(new Result.Exception<>(exception));
			}
			else if (response instanceof Response.Success success) {
				if (success.statusCode() != 200) {
					consumer.accept(new Result.Error<>(success.statusCode(), success.body()));
				} else {
					consumer.accept(new Result.Success<>(success.body(), success));
				}
			}
		}));
	}
}
