package me.adamix.sonoran.cad;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.adamix.sonoran.Sonoran;
import me.adamix.sonoran.cad.data.CadAccount;
import me.adamix.sonoran.http.handler.response.Response;
import me.adamix.sonoran.http.payload.JsonPayload;
import me.adamix.sonoran.http.request.SonoranRequest;
import me.adamix.sonoran.http.request.general.GetAccountRequest;
import org.apache.hc.core5.http.ParseException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
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

	public void getAccount(@NotNull String username, @NotNull Consumer<CadAccount> consumer) {
		sendRequest(new GetAccountRequest(username), (response) -> {
			if (response instanceof Response.Error(Exception exception)) {
				throw new RuntimeException("Unexpected exception while retrieving sonoran account", exception);
			}
			else if (response instanceof Response.Success success) {
				if (success.statusCode() != 200) {
					throw new RuntimeException("Request failed with status code: " + success.statusCode());
				} else {
					consumer.accept(CadAccount.parse(success.jsonBody()));
				}
			}
		});
	}
}
