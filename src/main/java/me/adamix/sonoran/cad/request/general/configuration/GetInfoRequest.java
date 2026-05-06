package me.adamix.sonoran.cad.request.general.configuration;

import me.adamix.sonoran.http.annotation.GetMethod;

@GetMethod(url = "/general/info", rateLimit = 10)
public interface GetInfoRequest {
}
