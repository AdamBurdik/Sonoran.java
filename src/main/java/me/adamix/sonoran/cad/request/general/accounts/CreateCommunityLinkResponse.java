package me.adamix.sonoran.cad.request.general.accounts;

import alpine.json.codec.Codec;
import me.adamix.sonoran.cad.data.SonoranCodecs;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;

public record CreateCommunityLinkResponse(
        @NotNull String code,
        @NotNull String communityUserId,
        @NotNull UUID communityUuid,
        @NotNull Instant expiresAt,
        int expiresInSeconds
) {
    public static final Codec<CreateCommunityLinkResponse> CODEC = Codec.<CreateCommunityLinkResponse>builder()
            .with("code", Codec.STRING, CreateCommunityLinkResponse::code)
            .with("communityUserId", Codec.STRING, CreateCommunityLinkResponse::communityUserId)
            .with("communityUuid", Codec.UUID, CreateCommunityLinkResponse::communityUuid)
            .with("expiresAt", SonoranCodecs.INSTANT, CreateCommunityLinkResponse::expiresAt)
            .with("expiresInSeconds", Codec.INTEGER, CreateCommunityLinkResponse::expiresInSeconds)
            .build(CreateCommunityLinkResponse::new);
}
