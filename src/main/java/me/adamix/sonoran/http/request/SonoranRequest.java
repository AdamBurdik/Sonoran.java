package me.adamix.sonoran.http.request;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface SonoranRequest {
	@NotNull String url();
	@NotNull String type();
	List<Object> data();
}
