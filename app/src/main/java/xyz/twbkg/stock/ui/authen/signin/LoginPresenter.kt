package xyz.twbkg.stock.ui.authen.signin

import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import xyz.twbkg.stock.R
import xyz.twbkg.stock.data.model.request.LoginRequest
import xyz.twbkg.stock.data.source.repository.impl.AuthenRepository
import xyz.twbkg.stock.extensions.handle
import javax.inject.Inject

class LoginPresenter @Inject constructor(
        var view: LoginContract.View,
        var authenRepository: AuthenRepository
) : LoginContract.Presenter {


    private var disposable = CompositeDisposable()


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
                        .subscribe({ _ ->
                            view.enableButton()
                            view.hideLoading()
                            view.navigationToMainActivity()
                        }, this::loginFail)

                disposable.add(service)

            }
        }
    }

    private fun loginFail(throwable: Throwable) {
        Timber.e("loginFail $throwable")
        view.enableButton()
        view.hideLoading()
        view.showErrorMessage(throwable.handle())
    }
}