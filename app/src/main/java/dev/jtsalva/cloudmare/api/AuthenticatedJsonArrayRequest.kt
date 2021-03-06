package dev.jtsalva.cloudmare.api

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import dev.jtsalva.cloudmare.Auth
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import com.android.volley.Response as JsonResponse

class AuthenticatedJsonArrayRequest(
    method: Int,
    url: String,
    jsonRequest: JSONArray?,
    listener: JsonResponse.Listener<JSONObject>,
    errorListener: JsonResponse.ErrorListener?
) : JsonRequest<JSONObject>(method, url, jsonRequest?.toString(), listener, errorListener) {
    override fun getHeaders(): MutableMap<String, String> = Auth.headers

    override fun parseNetworkResponse(response: NetworkResponse): JsonResponse<JSONObject> =
        try {
            val jsonString = String(
                response.data,
                Charset.forName(HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET))
            )
            JsonResponse.success(
                JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            JsonResponse.error(ParseError(e))
        } catch (je: JSONException) {
            JsonResponse.error(ParseError(je))
        }
}
