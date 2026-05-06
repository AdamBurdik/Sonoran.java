package me.adamix.sonoran.cad.request.generic;

public record Pagination(
        int limit,
        int offset,
        int total
) {
}
