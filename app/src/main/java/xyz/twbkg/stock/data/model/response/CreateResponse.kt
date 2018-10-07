package xyz.twbkg.stock.data.model.response

import com.google.gson.annotations.SerializedName


data class CreateResponse<T>(
        @SerializedName("success")
        var success: Boolean = false,
        @SerializedName("model")
        var model: T
)
