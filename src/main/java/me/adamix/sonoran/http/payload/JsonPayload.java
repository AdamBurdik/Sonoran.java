package me.adamix.sonoran.http.payload;

import com.google.gson.JsonObject;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.jetbrains.annotations.NotNull;

public record JsonPayload(@NotNull JsonObject jsonObject) implements Payload {
	@Override
	public @NotNull HttpEntity getEntity() {
		return new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON);
	}
}
