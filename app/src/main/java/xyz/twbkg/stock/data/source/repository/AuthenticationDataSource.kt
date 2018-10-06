package xyz.twbkg.stock.data.source.repository

import io.reactivex.Completable
import io.reactivex.Single
import xyz.twbkg.stock.data.model.request.LoginRequest
import xyz.twbkg.stock.data.model.request.SignUpRequest
import xyz.twbkg.stock.data.model.response.BaseResponse
import xyz.twbkg.stock.data.model.response.SigInResponse

interface AuthenticationDataSource {

    fun signUp(signUpRequest: SignUpRequest): Single<BaseResponse>

    fun signIn(loginRequest: LoginRequest): Single<SigInResponse>
}