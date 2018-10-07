package xyz.twbkg.stock.util

import okhttp3.Interceptor
import okhttp3.Response
import xyz.twbkg.stock.data.model.request.RequestHeader
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestInterceptor @Inject constructor(
        private val requestHeader: RequestHeader
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
                .header("Authorization", "Bearer " + requestHeader.loggedUser.accessToken)
                .method(original.method(), original.body())
        val newRequest = builder.build()

        return chain.proceed(newRequest)
    }
}