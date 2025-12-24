package me.adamix.sonoran.http.request.general.record;

import me.adamix.sonoran.cad.SonoranCad;

public interface EditRecordProvider extends GenericRecordProvider {
    // https://docs.sonoransoftware.com/cad/api-integration/api-endpoints/civilian/edit-character
    EditRecordProvider CHARACTER = (EditRecordProvider) GenericRecordProvider.create(SonoranCad.API_URL + "civilian/edit_character", "EDIT_CHARACTER");

    // https://docs.sonoransoftware.com/cad/api-integration/api-endpoints/general/custom-records/edit-record
    EditRecordProvider CUSTOM_RECORD = (EditRecordProvider) GenericRecordProvider.create(SonoranCad.API_URL + "general/edit_record", "EDIT_RECORD");
}
