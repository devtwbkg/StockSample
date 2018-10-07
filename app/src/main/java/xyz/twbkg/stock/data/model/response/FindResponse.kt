package xyz.twbkg.stock.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class FindResponse<T>(
        @SerializedName("content")
        @Expose
         var content: List<T>? = null,
        @SerializedName("page")
        @Expose
         var page: Int = 0,
        @SerializedName("size")
        @Expose
         var size: Int = 0,
        @SerializedName("totalElements")
        @Expose
         var totalElements: Int = 0,
        @SerializedName("totalPages")
        @Expose
         var totalPages: Int = 0,
        @SerializedName("last")
        @Expose
         var last: Boolean = false
)
