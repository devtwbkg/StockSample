//package xyz.twbkg.stock.extensions
//
//import java.io.IOException
//import java.lang.ref.WeakReference
//import java.net.SocketTimeoutException
//
//import io.reactivex.observers.DisposableObserver
//import okhttp3.ResponseBody
//import org.json.JSONObject
//import retrofit2.HttpException
//import xyz.twbkg.stock.application.BaseView
//import xyz.twbkg.stock.data.model.response.BaseResponse
//
//abstract class CallbackWrapper<T : BaseResponse>(var view: BaseView<T>) : DisposableObserver<T>() {
//    //BaseView is just a reference of a View in MVP
//    private val weakReference: WeakReference<BaseView<T>> = WeakReference(view)
//
//    protected abstract fun onSuccess(t: T)
//
//    override fun onNext(t: T) {
//        //You can return StatusCodes of different cases from your API and handle it here. I usually include these cases on BaseResponse and iherit it from every Response
//        onSuccess(t)
//    }
//
//    override fun onError(throwable: Throwable) {
//        var view = weakReference.get()
//        view?.let {
//            when (throwable) {
//                is HttpException -> {
//                    val responseBody = throwable.response().errorBody()
//                    view.onUnknownError(getErrorMessage(responseBody))
//                }
//                is SocketTimeoutException -> view.onTimeout()
//                is IOException -> view.onNetworkError()
//                else -> view.onUnknownError(throwable.message!!)
//            }
//        }
//    }
//
//    override fun onComplete() {
//
//    }
//
//    private fun getErrorMessage(responseBody: ResponseBody?): String {
//        return try {
//            val jsonObject = JSONObject(responseBody!!.string())
//            jsonObject.getString("message")
//        } catch (e: Exception) {
//            e.message!!
//        }
//
//    }
//}