package xyz.twbkg.stock.extensions

import retrofit2.HttpException
import xyz.twbkg.stock.R
import java.net.ConnectException
import java.net.SocketTimeoutException

fun Throwable.handle(): Int {
    return if (this is HttpException) {
        when (this.code()) {
            504 -> R.string.error_connection_timeout
            else -> {
                R.string.unknown_error
            }
        }

    } else {
        when (this) {
            is SocketTimeoutException -> {
                R.string.error_connection_timeout
            }
            is ConnectException -> {
                R.string.error_data_or_wifi_is_not_available
            }
            else -> {
                R.string.unknown_error
            }
        }
    }
}