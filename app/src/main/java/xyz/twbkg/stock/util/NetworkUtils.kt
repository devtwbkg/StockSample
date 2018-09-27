package xyz.twbkg.stock.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import javax.inject.Inject

/**
 * Checks if a network connection exists.
 */
class NetworkUtils @Inject constructor(val context: Context) {

    fun hasNetworkConnection(): Boolean {
        val connectivityManager =
                context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
