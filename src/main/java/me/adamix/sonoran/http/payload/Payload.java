package me.adamix.sonoran.http.payload;

import org.apache.hc.core5.http.HttpEntity;
import org.jetbrains.annotations.NotNull;

public interface Payload {
	@NotNull HttpEntity getEntity();
}
