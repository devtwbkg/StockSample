package xyz.twbkg.stock.ui.authen.signin

import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import xyz.twbkg.stock.MainApp.Companion.token
import xyz.twbkg.stock.R
import xyz.twbkg.stock.data.model.LoggedUser
import xyz.twbkg.stock.data.model.db.User
import xyz.twbkg.stock.data.model.request.LoginRequest
import xyz.twbkg.stock.data.model.request.RequestHeader
import xyz.twbkg.stock.data.source.repository.impl.AuthenRepository
import xyz.twbkg.stock.data.source.repository.impl.UserRepository
import xyz.twbkg.stock.extensions.handle
import javax.inject.Inject

class LoginPresenter @Inject constructor(
        var view: LoginContract.View,
        var authenRepository: AuthenRepository,
        var userRepository: UserRepository
) : LoginContract.Presenter {


    private var disposable = CompositeDisposable()

    @Inject
    lateinit var loggedUser: LoggedUser
    @Inject
    lateinit var requestHeader: RequestHeader

    override fun dropView() {

    }

    override fun restoreSavedInstanceState(savedInstanceState: Bundle) {
    }

    override fun restoreArgument(arguments: Bundle) {
    }

    override fun refreshData() {
    }


    override fun signIn(userNameOrEmail: String, password: String) {
        if (userNameOrEmail.isEmpty()) {
            view.invalidUserOrEmail(R.string.error_invalid_email_or_password)
            return
        }

        if (password.isEmpty()) {
            view.invalidPassword(R.string.error_invalid_password)
            return
        }
        if (userNameOrEmail.isNotEmpty() && password.isNotEmpty()) {
            view.disableButton()
            view.showLoading()
            LoginRequest(userNameOrEmail, password).also {
                val service = authenRepository.signIn(it)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ response ->
                            view.enableButton()
                            view.hideLoading()
                            loggedUser.accessToken = response.accessToken
                            requestHeader.loggedUser.accessToken = response.accessToken
                            token = "${response.tokenType} ${response.accessToken}"
                            cacheUserSignIn(response.accessToken)
                            view.navigationToMainActivity()
                        }, this::loginFail)

                disposable.add(service)

            }
        }
    }

    private fun cacheUserSignIn(accessToken: String) {

//        disposable.add(userRepository.findUserLoggedIn()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe {
//                   if(it!=null){
//                       it.token = accessToken
//                   }
//                })
        User(token = accessToken)
                .also {
                    userRepository.save(it)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::cacheUserSuccess, this::cacheUserFail)
                }
    }

    override fun isLoggedIn() {
        disposable.add(userRepository.findLastSignIn()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ accessToken ->
                    if (accessToken.isNotEmpty()) {
                        token = accessToken
                        loggedUser.accessToken = accessToken
                        requestHeader.loggedUser.accessToken = accessToken
                        view.navigationToMainActivity()
                    }
                }, this::checkCacheUserLoginFail)
        )
    }

    private fun cacheUserSuccess() {
        Timber.i("cache user success")
    }

    private fun cacheUserFail(throwable: Throwable) {
        Timber.e("cache user fail $throwable")
    }

    private fun checkCacheUserLoginFail(throwable: Throwable) {
        Timber.e("check cache user is loggedin fail $throwable")
    }

    private fun loginFail(throwable: Throwable) {
        Timber.e("loginFail $throwable")
        view.enableButton()
        view.hideLoading()
        view.showErrorMessage(throwable.handle())
    }
}