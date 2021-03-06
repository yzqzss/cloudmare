package dev.jtsalva.cloudmare.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.jtsalva.cloudmare.adapter.fit

@JsonClass(generateAdapter = true)
open class Response(
    @field:Json(name = "success") val success: Boolean,
    @field:Json(name = "errors") val errors: List<Error> = emptyList(),
    @field:Json(name = "messages") val messages: List<Message> = emptyList()
) {

    companion object {
        private const val MAX_ERROR_MESSAGE_LENGTH = 600

        fun createWithErrors(vararg errors: Error) =
            Response(success = false, errors = errors.run {
                mutableListOf<Error>().apply {
                    for (err in this@run) add(err)
                }
            })
    }

    open val result: Any? = null

    val failure: Boolean get() = !success

    val firstErrorMessage: String get() = with(errors[0].mostRelevantError) {
        // Intercept and replace user obscure error messages
        when (code) {
            0 -> "Access denied, more permission required"
            6102, 6103 -> "Invalid email or api key format"
            9041 -> "This DNS record cannot be proxied"
            9103 -> "Invalid email or api key"
            9106, 9107 -> "Missing email or api key"
            else -> message.fit(MAX_ERROR_MESSAGE_LENGTH)
        }
    }

    val isLocalError: Boolean get() = errors[0].code == Request.LOCAL_ERROR_CODE

    @JsonClass(generateAdapter = true)
    data class Error(
        @field:Json(name = "code") val code: Int,
        @field:Json(name = "message") val message: String,

        @field:Json(name = "error_chain")
        val errorChain: List<Error>? = null
    ) {

        val mostRelevantError: Error get() = errorChain?.get(0)?.mostRelevantError ?: this
    }

    @JsonClass(generateAdapter = true)
    data class Message(
        @field:Json(name = "code") val code: Int,
        @field:Json(name = "message") val message: String
    )
}
