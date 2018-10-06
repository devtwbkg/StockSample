package xyz.twbkg.stock.data.model.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUpRequest(
        var email: String = "",
        var username: String = "",
        var password: String = "",
        var name: String = ""
) : Parcelable