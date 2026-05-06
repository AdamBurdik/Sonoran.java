package me.adamix.sonoran.cad.request.general.accounts;

import me.adamix.sonoran.cad.data.CADAccount;
import me.adamix.sonoran.cad.request.generic.Pagination;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record GetAccountsResponse(
        @NotNull Pagination pagination,
        @NotNull List<CADAccount> accounts
) {
}
