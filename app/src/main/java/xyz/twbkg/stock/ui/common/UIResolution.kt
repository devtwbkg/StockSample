package xyz.twbkg.stock.ui.common

import timber.log.Timber
import xyz.twbkg.stock.R
import java.net.ConnectException
import javax.inject.Inject


open class UIResolution @Inject constructor(private val uiResolver: UIResolver) : ResolutionByCase() {
    override fun onUnknownError() {
        uiResolver.showSnackBar(R.string.error_unknown_error)
    }

    override fun badRequest(message: String) {
        uiResolver.showDialogError(message)
    }

    override fun onUnauthorized() {
        uiResolver.showDialogError(R.string.error_unauthorized)
    }

    override fun onConnectivityAvailable() {
        uiResolver.hidePersistentSnackBar()
    }

    override fun onConnectivityUnavailable() {
        uiResolver.showPersistentSnackBar(R.string.error_no_network_connection)
    }

    override fun onNotFound() {
        uiResolver.showSnackBar(R.string.error_not_found)
    }

    override fun onServiceUnavailable() {
        uiResolver.showSnackBar(R.string.error_service_unavailable)
    }

    override fun onInternalServerError() {
        uiResolver.showSnackBar(R.string.error_http_exception)
    }

    override fun onGenericRxException(t: Throwable) {
        t.printStackTrace()
        Timber.e("onGenericRxException $t")

        if (t is ConnectException) {
            if (t.message?.trim().equals("Network is unreachable", true)) {
                uiResolver.showToastLongMessage(R.string.error_data_or_wifi_is_not_available)
            }
        }
    }

    override fun onNetworkLocationError() {
        uiResolver.showSnackBar(R.string.error_enable_gps)
    }
}