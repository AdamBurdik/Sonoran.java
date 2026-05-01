package me.adamix.sonoran.cad.data;

import alpine.json.codec.Codec;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
        public static final Codec<Permissions> CODEC = new Codec<>() {
            @Override
            public <R> Permissions decode(alpine.json.codec.Transcoder<R> transcoder, R value) {
                return new Permissions(
                        decodeBool(transcoder, value, "civilian"),
                        decodeBool(transcoder, value, "lawyer"),
                        decodeBool(transcoder, value, "dmv"),
                        decodeBool(transcoder, value, "police"),
                        decodeBool(transcoder, value, "fire"),
                        decodeBool(transcoder, value, "ems"),
                        decodeBool(transcoder, value, "dispatch"),
                        decodeBool(transcoder, value, "admin"),
                        decodeBool(transcoder, value, "polRecAdd"),
                        decodeBool(transcoder, value, "polRecEdit"),
                        decodeBool(transcoder, value, "polRecRemove"),
                        decodeBool(transcoder, value, "polSuper"),
                        decodeBool(transcoder, value, "polEditUnit"),
                        decodeBool(transcoder, value, "polEditOtherUnit"),
                        decodeBool(transcoder, value, "selfDispatch"),
                        decodeBool(transcoder, value, "liveMap"),
                        decodeBool(transcoder, value, "medRecAdd"),
                        decodeBool(transcoder, value, "medRecEdit"),
                        decodeBool(transcoder, value, "medRecRemove"),
                        decodeBool(transcoder, value, "medSuper"),
                        decodeBool(transcoder, value, "fireRecAdd"),
                        decodeBool(transcoder, value, "fireRecEdit"),
                        decodeBool(transcoder, value, "fireRecRemove"),
                        decodeBool(transcoder, value, "fireSuper"),
                        decodeBool(transcoder, value, "dmvRecAdd"),
                        decodeBool(transcoder, value, "dmvRecEdit"),
                        decodeBool(transcoder, value, "dmvRecRemove"),
                        decodeBool(transcoder, value, "dmvSuper"),
                        decodeBool(transcoder, value, "modifyStreetSigns"),
                        decodeBool(transcoder, value, "lawRecAdd"),
                        decodeBool(transcoder, value, "lawRecEdit"),
                        decodeBool(transcoder, value, "lawRecRemove"),
                        decodeBool(transcoder, value, "lawSuper"),
                        decodeBool(transcoder, value, "adminAccounts"),
                        decodeBool(transcoder, value, "adminPermissionKeys"),
                        decodeBool(transcoder, value, "adminCustomization"),
                        decodeBool(transcoder, value, "adminDepartments"),
                        decodeBool(transcoder, value, "adminTenCodes"),
                        decodeBool(transcoder, value, "adminPenalCodes"),
                        decodeBool(transcoder, value, "adminInGameIntegration"),
                        decodeBool(transcoder, value, "adminDiscordIntegration"),
                        decodeBool(transcoder, value, "adminLimits"),
                        decodeBool(transcoder, value, "adminLogs")
                );
            }

            @Override
            public <R> R encode(alpine.json.codec.Transcoder<R> transcoder, Permissions value) {
                Map<String, R> fields = new LinkedHashMap<>();
                encodeBool(fields, transcoder, "civilian", value.civilian());
                encodeBool(fields, transcoder, "lawyer", value.lawyer());
                encodeBool(fields, transcoder, "dmv", value.dmv());
                encodeBool(fields, transcoder, "police", value.police());
                encodeBool(fields, transcoder, "fire", value.fire());
                encodeBool(fields, transcoder, "ems", value.ems());
                encodeBool(fields, transcoder, "dispatch", value.dispatch());
                encodeBool(fields, transcoder, "admin", value.admin());
                encodeBool(fields, transcoder, "polRecAdd", value.polRecAdd());
                encodeBool(fields, transcoder, "polRecEdit", value.polRecEdit());
                encodeBool(fields, transcoder, "polRecRemove", value.polRecRemove());
                encodeBool(fields, transcoder, "polSuper", value.polSuper());
                encodeBool(fields, transcoder, "polEditUnit", value.polEditUnit());
                encodeBool(fields, transcoder, "polEditOtherUnit", value.polEditOtherUnit());
                encodeBool(fields, transcoder, "selfDispatch", value.selfDispatch());
                encodeBool(fields, transcoder, "liveMap", value.liveMap());
                encodeBool(fields, transcoder, "medRecAdd", value.medRecAdd());
                encodeBool(fields, transcoder, "medRecEdit", value.medRecEdit());
                encodeBool(fields, transcoder, "medRecRemove", value.medRecRemove());
                encodeBool(fields, transcoder, "medSuper", value.medSuper());
                encodeBool(fields, transcoder, "fireRecAdd", value.fireRecAdd());
                encodeBool(fields, transcoder, "fireRecEdit", value.fireRecEdit());
                encodeBool(fields, transcoder, "fireRecRemove", value.fireRecRemove());
                encodeBool(fields, transcoder, "fireSuper", value.fireSuper());
                encodeBool(fields, transcoder, "dmvRecAdd", value.dmvRecAdd());
                encodeBool(fields, transcoder, "dmvRecEdit", value.dmvRecEdit());
                encodeBool(fields, transcoder, "dmvRecRemove", value.dmvRecRemove());
                encodeBool(fields, transcoder, "dmvSuper", value.dmvSuper());
                encodeBool(fields, transcoder, "modifyStreetSigns", value.modifyStreetSigns());
                encodeBool(fields, transcoder, "lawRecAdd", value.lawRecAdd());
                encodeBool(fields, transcoder, "lawRecEdit", value.lawRecEdit());
                encodeBool(fields, transcoder, "lawRecRemove", value.lawRecRemove());
                encodeBool(fields, transcoder, "lawSuper", value.lawSuper());
                encodeBool(fields, transcoder, "adminAccounts", value.adminAccounts());
                encodeBool(fields, transcoder, "adminPermissionKeys", value.adminPermissionKeys());
                encodeBool(fields, transcoder, "adminCustomization", value.adminCustomization());
                encodeBool(fields, transcoder, "adminDepartments", value.adminDepartments());
                encodeBool(fields, transcoder, "adminTenCodes", value.adminTenCodes());
                encodeBool(fields, transcoder, "adminPenalCodes", value.adminPenalCodes());
                encodeBool(fields, transcoder, "adminInGameIntegration", value.adminInGameIntegration());
                encodeBool(fields, transcoder, "adminDiscordIntegration", value.adminDiscordIntegration());
                encodeBool(fields, transcoder, "adminLimits", value.adminLimits());
                encodeBool(fields, transcoder, "adminLogs", value.adminLogs());
                return transcoder.encodeObject(fields);
            }
        };

        private static <R> boolean decodeBool(alpine.json.codec.Transcoder<R> transcoder, R value, String key) {
            return transcoder.decodeBoolean(transcoder.decodeObjectField(value, key));
        }

        private static <R> void encodeBool(Map<String, R> fields, alpine.json.codec.Transcoder<R> transcoder, String key, boolean value) {
            fields.put(key, transcoder.encodeBoolean(value));
        }
    }

    public static final Codec<CADAccount> CODEC = Codec.<CADAccount>builder()
            .with("uuid", Codec.UUID, CADAccount::uuid)
            .with("username", Codec.STRING, CADAccount::username)
            .with("communityUserId", Codec.STRING, CADAccount::communityUserId)
            .with("status", Codec.ordinal(Status.class), CADAccount::status)
            .with("joined", SonoranCodecs.INSTANT, CADAccount::joined)
            .with("lastLogin", SonoranCodecs.INSTANT, CADAccount::lastLogin)
            .with("permissions", Permissions.CODEC, CADAccount::permissions)
            .with("apiIds", Codec.STRING.list(), CADAccount::apiIds)
            .build(CADAccount::new);
}
