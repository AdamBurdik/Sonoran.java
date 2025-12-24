package me.adamix.sonoran.http.request.general.record;

import me.adamix.sonoran.cad.SonoranCad;

public interface CreateRecordProvider extends GenericRecordProvider {
    // https://docs.sonoransoftware.com/cad/api-integration/api-endpoints/civilian/new-character
    CreateRecordProvider CHARACTER = (CreateRecordProvider) GenericRecordProvider.create(SonoranCad.API_URL + "civilian/new_character", "NEW_CHARACTER");

    // https://docs.sonoransoftware.com/cad/api-integration/api-endpoints/general/custom-records/new-record
    CreateRecordProvider CUSTOM_RECORD = (CreateRecordProvider) GenericRecordProvider.create(SonoranCad.API_URL + "general/new_record", "NEW_RECORD");
}
