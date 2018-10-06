package xyz.twbkg.stock.data.model.response

import com.google.gson.annotations.SerializedName

data class SigInResponse(
        @SerializedName("accessToken")
        var accessToken: String = "",
        @SerializedName("tokenType")
        var tokenType: String = ""
) {
}