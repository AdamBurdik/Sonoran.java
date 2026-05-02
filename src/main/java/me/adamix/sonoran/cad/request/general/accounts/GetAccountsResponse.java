package me.adamix.sonoran.cad.request.general.accounts;

import alpine.json.codec.Codec;
import me.adamix.sonoran.cad.data.CADAccount;
import me.adamix.sonoran.cad.request.generic.Pagination;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record GetAccountsResponse(
        @NotNull Pagination pagination,
        @NotNull List<CADAccount> accounts
) {
    public static final Codec<GetAccountsResponse> CODEC = Codec.<GetAccountsResponse>builder()
            .with("pagination", Pagination.CODEC, GetAccountsResponse::pagination)
            .with("accounts", CADAccount.CODEC.list(), GetAccountsResponse::accounts)
            .build(GetAccountsResponse::new);

}
