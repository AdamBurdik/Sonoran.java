package me.adamix.sonoran.cad.request.general.records;

import me.adamix.sonoran.http.annotation.Param;
import me.adamix.sonoran.http.annotation.PatchMethod;

import java.util.Map;

@PatchMethod(url = "/general/records/{recordId}", rateLimit = 10)
@Param(key = "communityUserId", type = String.class, required = false)
@Param(key = "roblox", type = String.class, required = false)
@Param(key = "accountUuid", type = String.class, required = false)
@Param(key = "recordId", type = Integer.class)
@Param(key = "templateId", type = Integer.class, required = false)
@Param(key = "useDictionary", type = Boolean.class, required = false)
@Param(key = "replaceValues", type = Map.class, required = false)
@Param(key = "record", type = Map.class, required = false)
public interface UpdateRecordRequest {
}
