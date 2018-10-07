package xyz.twbkg.stock.ui.unit

import android.view.View
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import xyz.twbkg.stock.application.BaseFragment
import xyz.twbkg.stock.ui.common.Resolution

abstract class BaseUnitFragment : BaseFragment() {
    abstract fun rootView(): View
}
//
//}, Resolution {
//
//    override fun onHttpException(httpException: HttpException) {
//        val code = httpException.code()
//        when (code) {
//            500 -> onInternalServerError()
//            503 -> onServiceUnavailable()
//            404 -> onNotFound()
//            else -> onInternalServerError()
//        }
//    }
//
//    abstract fun onInternalServerError()
//
//    abstract fun onNotFound()
//
//    abstract fun onServiceUnavailable()
//
//    abstract fun rootView(): View
//}