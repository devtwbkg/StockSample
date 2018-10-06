package xyz.twbkg.stock.ui.authen.signin

import android.support.annotation.StringRes
import xyz.twbkg.stock.application.BasePresenter
import xyz.twbkg.stock.application.BaseView
import xyz.twbkg.stock.ui.unit.UnitContract

interface LoginContract {
    interface View : BaseView<UnitContract.Presenter> {
        fun showLoading()
        fun hideLoading()
        fun enableButton()
        fun disableButton()
        fun navigationToMainActivity()
        fun invalidUserOrEmail(@StringRes message: Int)
        fun invalidPassword(@StringRes message: Int)
        fun showErrorMessage(@StringRes message: Int)
        fun showErrorMessage(@StringRes message: String)
    }

    interface Presenter : BasePresenter<UnitContract.View> {
        fun signIn(userNameOrEmail: String, password: String)
    }

}