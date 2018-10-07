package xyz.twbkg.stock.extensions

import android.content.Context
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import okhttp3.ResponseBody
import xyz.twbkg.stock.R
import xyz.twbkg.stock.data.model.response.ApiResponse
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException


fun Throwable.handle(): Int {

    return if (this is HttpException) {

        when (this.code()) {
            504 -> R.string.error_connection_timeout
            400 -> R.string.please_check_object_value
            401 -> {
                R.string.error_you_are_not_authorized_to_access
            }
            else -> {
                R.string.error_unknown_error
            }
        }

    } else {
        when (this) {
            is SocketTimeoutException -> {
                R.string.error_connection_timeout
            }
            is ConnectException -> {
                R.string.error_service_unavailable
            }
            else -> {
                R.string.error_unknown_error
            }
        }
    }
}


fun Throwable.isHttpException(): Boolean {
    return this is HttpException
}

fun Throwable.httpExceptionHandler(context: Context): String {
    return when (this) {
        is HttpException -> {
            when (this.code()) {
                400 -> {
                    val responseBody = this.response().errorBody()
                    getErrorMessage(responseBody)
                }
                401 -> context.getString(R.string.error_unauthorized)
                500 -> context.getString(R.string.error_unauthorized)
                503 -> context.getString(R.string.error_service_unavailable)
                504 -> context.getString(R.string.error_no_network_connection)
                else -> context.getString(R.string.error_unknown_error)
            }

        }
        else -> context.getString(R.string.error_unknown_error)
    }
}

fun Throwable.handleException(): Int {
    return when (this) {
        is SocketTimeoutException -> R.string.error_connection_timeout
        is ConnectException -> R.string.error_service_unavailable
        is IOException -> R.string.error_data_or_wifi_is_not_available
        else -> R.string.error_unknown_error
    }
}

private fun getErrorMessage(responseBody: ResponseBody?): String {
    return try {
        val json = Gson().fromJson(responseBody?.string(), ApiResponse::class.java)
        json.message
    } catch (e: Exception) {
        e.message!!
    }
}