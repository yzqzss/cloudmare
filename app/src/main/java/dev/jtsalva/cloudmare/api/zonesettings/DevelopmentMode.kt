package dev.jtsalva.cloudmare.api.zonesettings

import com.squareup.moshi.Json
import dev.jtsalva.cloudmare.api.DateString

data class DevelopmentMode(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "value") val value: String,
    @field:Json(name = "editable") val editable: Boolean,
    @field:Json(name = "modified_on") val modifiedOn: DateString,
    @field:Json(name = "time_remaining") val timeRemaining: Int
) {
//    enum class Value {
//        OFF,
//        ON;
//
//        override fun toString(): String = super.toString().toLowerCase()
//    }

    companion object Value {
        const val OFF = "off"
        const val ON = "on"
    }
}