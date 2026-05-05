package me.adamix.sonoran.cad.request.civilian;

import me.adamix.sonoran.http.annotation.GetMethod;
import me.adamix.sonoran.http.annotation.Param;

@GetMethod(url = "/civilian/characters", rateLimit = 10)
@Param(key = "communityUserId", type = String.class, required = false)
@Param(key = "roblox", type = Integer.class, required = false)
@Param(key = "accountUuid", type = String.class, required = false)
public class GetCharactersRequest {
}
