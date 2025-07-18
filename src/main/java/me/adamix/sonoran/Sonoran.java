package me.adamix.sonoran;

import me.adamix.sonoran.cad.SonoranCad;
import me.adamix.sonoran.http.handler.DefaultRequestHandler;
import me.adamix.sonoran.http.handler.RequestHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Sonoran {
	private final @NotNull RequestHandler requestHandler;
	private final @Nullable SonoranCad cad;

	private Sonoran(
			@NotNull RequestHandler requestHandler,
			@Nullable String cadToken,
			@Nullable String cadCommunityId
	) {
		this.requestHandler = requestHandler;
		if (cadToken != null && cadCommunityId != null) {
			cad = new SonoranCad(this, cadToken, cadCommunityId);
		} else {
			cad = null;
		}
	}

	public static @NotNull Builder builder() {
		return new Builder();
	}

	public @NotNull SonoranCad cad() {
		if (cad == null) {
			throw new IllegalStateException("Sonoran CAD is not configured in the builder");
		}
		return cad;
	}

	public @NotNull RequestHandler requestHandler() {
		return requestHandler;
	}

	public void shutdown() {
		requestHandler.scheduleShutdown();
	}

	public static class Builder {
		private @Nullable String cadToken;
		private @Nullable String cadCommunityId;

		private @NotNull RequestHandler requestHandler = new DefaultRequestHandler();

		public @NotNull Builder withCad(@NotNull String token, @NotNull String communityId) {
			this.cadToken = token;
			this.cadCommunityId = communityId;
			return this;
		}

		public @NotNull Builder withRequestHandler(@NotNull RequestHandler requestHandler) {
			this.requestHandler = requestHandler;
			return this;
		}

		public @NotNull Sonoran build() {
			return new Sonoran(
					requestHandler,
					cadToken,
					cadCommunityId
			);
		}
	}
}
