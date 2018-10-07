package xyz.twbkg.stock.data.source.repository.impl

import io.reactivex.Single
import xyz.twbkg.stock.data.model.request.LoginRequest
import xyz.twbkg.stock.data.model.request.SignUpRequest
import xyz.twbkg.stock.data.model.response.ApiResponse
import xyz.twbkg.stock.data.model.response.SigInResponse
import xyz.twbkg.stock.data.source.remote.authen.AuthenService
import xyz.twbkg.stock.data.source.repository.AuthenticationDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenRepository @Inject constructor(
        var service: AuthenService
) : AuthenticationDataSource {
    override fun getTotken(): String {
        return ""
    }

    override fun signUp(signUpRequest: SignUpRequest): Single<ApiResponse> {
        return service.signUp(signUpRequest)
    }

    override fun signIn(loginRequest: LoginRequest): Single<SigInResponse> {
        return service.signIn(loginRequest)
    }
}