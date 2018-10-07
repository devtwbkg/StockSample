package xyz.twbkg.stock.data.source.remote.authen

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import xyz.twbkg.stock.data.model.request.LoginRequest
import xyz.twbkg.stock.data.model.request.SignUpRequest
import xyz.twbkg.stock.data.model.response.ApiResponse
import xyz.twbkg.stock.data.model.response.SigInResponse

interface AuthenService {

    @POST("/api/v1/auth/signup")
    fun signUp(@Body signUpRequest: SignUpRequest): Single<ApiResponse>

    @POST("/api/v1/auth/signin")
    fun signIn(@Body loginRequest: LoginRequest): Single<SigInResponse>
}