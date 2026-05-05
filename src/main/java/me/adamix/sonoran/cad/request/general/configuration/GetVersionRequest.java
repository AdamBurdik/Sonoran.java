package me.adamix.sonoran.cad.request.general.configuration;

import me.adamix.sonoran.http.annotation.GetMethod;

@GetMethod(url = "/general/version", rateLimit = 10)
public interface GetVersionRequest {
}
