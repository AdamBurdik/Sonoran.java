package me.adamix.sonoran.cad.request.general.accounts;

import me.adamix.sonoran.http.annotation.GetMethod;
import me.adamix.sonoran.http.annotation.Param;

@GetMethod(url = "/general/accounts/account", rateLimit = 10)
@Param(key = "communityUserId", type = String.class, required = false)
@Param(key = "roblox", type = Integer.class, required = false)
@Param(key = "accountUuid", type = String.class, required = false)
@Param(key = "username", type = String.class, required = false)
public interface GetAccountRequest {
}
