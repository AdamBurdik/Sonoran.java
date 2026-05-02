package me.adamix.sonoran.cad.request.general.accounts;

import me.adamix.sonoran.http.annotation.GetMethod;
import me.adamix.sonoran.http.annotation.Param;

@GetMethod(url = "/general/accounts", rateLimit = 10)
@Param(key = "limit", type = Integer.class)
@Param(key = "offset", type = Integer.class)
@Param(key = "status", type = Integer.class)
@Param(key = "username", type = String.class)
public interface GetAccountsRequest {
}
