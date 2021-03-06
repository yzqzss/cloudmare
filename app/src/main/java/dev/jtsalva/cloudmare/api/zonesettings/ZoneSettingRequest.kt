package dev.jtsalva.cloudmare.api.zonesettings

import dev.jtsalva.cloudmare.CloudMareActivity
import dev.jtsalva.cloudmare.api.Request
import dev.jtsalva.cloudmare.api.toJsonString
import org.json.JSONObject

class ZoneSettingRequest(context: CloudMareActivity) : Request<ZoneSettingRequest>(context) {

    suspend fun list(zoneId: String): ZoneSettingListResponse {
        return httpGet("zones/$zoneId/settings")
    }

    suspend fun update(zoneId: String, vararg zoneSettings: ZoneSetting): ZoneSettingListResponse {
        val strings = mutableListOf<String>()
        zoneSettings.forEach { strings.add(it.toJsonString()) }

        val payload = JSONObject("{\"items\":[${strings.joinToString(",")}]}")

        return httpPatch("zones/$zoneId/settings", payload)
    }
}
