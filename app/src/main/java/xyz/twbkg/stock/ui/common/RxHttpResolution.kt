package xyz.twbkg.stock.ui.common

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException

interface RxHttpResolution {
    fun onHttpException(httpException: HttpException)
    fun onGenericRxException(t: Throwable)
}

interface NetworkConnectivityResolution {
    fun onConnectivityAvailable()
    fun onConnectivityUnavailable()
}

interface LocationRequestResolution {
    fun onNetworkLocationError()
}

interface Resolution : RxHttpResolution, NetworkConnectivityResolution, LocationRequestResolution {
}