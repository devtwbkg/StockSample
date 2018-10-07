package xyz.twbkg.stock.data.source.repository

import io.reactivex.Single
import xyz.twbkg.stock.data.model.request.LoginRequest
import xyz.twbkg.stock.data.model.request.SignUpRequest
import xyz.twbkg.stock.data.model.response.ApiResponse
import xyz.twbkg.stock.data.model.response.SigInResponse

interface AuthenticationDataSource {

    fun signUp(signUpRequest: SignUpRequest): Single<ApiResponse>

    fun signIn(loginRequest: LoginRequest): Single<SigInResponse>

    fun getTotken(): String
}