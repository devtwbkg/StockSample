package xyz.twbkg.stock.data.source.remote.authen

import io.reactivex.Single
import xyz.twbkg.stock.data.model.request.LoginRequest
import xyz.twbkg.stock.data.model.request.SignUpRequest
import xyz.twbkg.stock.data.model.response.ApiResponse
import xyz.twbkg.stock.data.model.response.SigInResponse
import xyz.twbkg.stock.data.source.repository.AuthenticationDataSource
import javax.inject.Inject

class AuthenRemoteDataSource @Inject constructor(
        var service: AuthenService
) : AuthenticationDataSource {
    override fun signUp(signUpRequest: SignUpRequest): Single<ApiResponse> {
        return service.signUp(signUpRequest)
    }

    override fun signIn(loginRequest: LoginRequest): Single<SigInResponse> {
        return service.signIn(loginRequest)
    }

    override fun getTotken(): String {
        return ""
    }
}