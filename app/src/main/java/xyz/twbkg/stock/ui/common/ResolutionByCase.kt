package xyz.twbkg.stock.ui.common

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import okhttp3.ResponseBody
import xyz.twbkg.stock.data.model.response.ApiResponse

abstract class ResolutionByCase : Resolution {

    override fun onHttpException(httpException: HttpException) {
        val code = httpException.code()
        when (code) {
            500 -> onInternalServerError()
            503 -> onServiceUnavailable()
            404 -> onNotFound()
            401 -> onUnauthorized()
            400 -> {
                val responseBody = httpException.response().errorBody()
                val messageBody = getErrorMessage(responseBody)

                badRequest(messageBody)
            }
            else -> onUnknownError()
        }
    }

    abstract fun badRequest(message: String)

    abstract fun onUnauthorized()

    abstract fun onUnknownError()

    abstract fun onInternalServerError()

    abstract fun onNotFound()

    abstract fun onServiceUnavailable()


    private fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val json = Gson().fromJson(responseBody?.string(), ApiResponse::class.java)
            json.message
        } catch (e: Exception) {
            ""
        }
    }
}