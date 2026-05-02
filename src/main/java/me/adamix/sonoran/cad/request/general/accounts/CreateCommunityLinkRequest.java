package me.adamix.sonoran.cad.request.general.accounts;

import me.adamix.sonoran.http.annotation.Param;
import me.adamix.sonoran.http.annotation.PostMethod;

@PostMethod(url = "/general/links", rateLimit = 30)
@Param(key = "communityUserId", type = String.class)
public interface CreateCommunityLinkRequest {
}
