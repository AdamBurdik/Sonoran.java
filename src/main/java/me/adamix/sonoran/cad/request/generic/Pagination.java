package me.adamix.sonoran.cad.request.generic;

import alpine.json.codec.Codec;

public record Pagination(
        int limit,
        int offset,
        int total
) {
    public static final Codec<Pagination> CODEC = Codec.<Pagination>builder()
            .with("limit", Codec.INTEGER, Pagination::limit)
            .with("offset", Codec.INTEGER, Pagination::offset)
            .with("total", Codec.INTEGER, Pagination::total)
            .build(Pagination::new);

}
