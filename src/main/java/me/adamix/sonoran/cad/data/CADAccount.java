package me.adamix.sonoran.cad.data;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CADAccount(
        @NotNull UUID uuid,
        @NotNull String username,
        @NotNull String communityUserId,
        @NotNull Status status,
        @NotNull Instant joined,
        @NotNull Instant lastLogin,
        @NotNull Permissions permissions,
        @NotNull List<String> apiIds
) {
    public enum Status {
        PENDING,
        VALIDATED,
        EXPIRED,
        REMOVED,
        BANNED
    }

    public record Permissions(
            boolean civilian,
            boolean lawyer,
            boolean dmv,
            boolean police,
            boolean fire,
            boolean ems,
            boolean dispatch,
            boolean admin,
            boolean polRecAdd,
            boolean polRecEdit,
            boolean polRecRemove,
            boolean polSuper,
            boolean polEditUnit,
            boolean polEditOtherUnit,
            boolean selfDispatch,
            boolean liveMap,
            boolean medRecAdd,
            boolean medRecEdit,
            boolean medRecRemove,
            boolean medSuper,
            boolean fireRecAdd,
            boolean fireRecEdit,
            boolean fireRecRemove,
            boolean fireSuper,
            boolean dmvRecAdd,
            boolean dmvRecEdit,
            boolean dmvRecRemove,
            boolean dmvSuper,
            boolean modifyStreetSigns,
            boolean lawRecAdd,
            boolean lawRecEdit,
            boolean lawRecRemove,
            boolean lawSuper,
            boolean adminAccounts,
            boolean adminPermissionKeys,
            boolean adminCustomization,
            boolean adminDepartments,
            boolean adminTenCodes,
            boolean adminPenalCodes,
            boolean adminInGameIntegration,
            boolean adminDiscordIntegration,
            boolean adminLimits,
            boolean adminLogs
    ) {

    }
}
