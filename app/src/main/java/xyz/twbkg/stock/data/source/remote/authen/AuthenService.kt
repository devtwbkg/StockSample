package xyz.twbkg.stock.data.source.remote.authen

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import xyz.twbkg.stock.data.model.request.LoginRequest
import xyz.twbkg.stock.data.model.request.SignUpRequest
import xyz.twbkg.stock.data.model.response.BaseResponse
import xyz.twbkg.stock.data.model.response.SigInResponse

interface AuthenService {

    @POST("/signup")
    fun signUp(@Body signUpRequest: SignUpRequest): Single<BaseResponse>

    @POST("/signin")
    fun signIn(@Body loginRequest: LoginRequest): Single<SigInResponse>
}