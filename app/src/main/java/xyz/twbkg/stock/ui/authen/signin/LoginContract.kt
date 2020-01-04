package xyz.twbkg.stock.ui.authen.signin

import androidx.annotation.StringRes
import xyz.twbkg.stock.application.BasePresenter
import xyz.twbkg.stock.application.BaseView
import xyz.twbkg.stock.ui.unit.UnitContract

interface LoginContract {
    interface View : BaseView<UnitContract.Presenter> {
        fun enableButton()
        fun disableButton()
        fun navigationToMainActivity()
        fun invalidUserOrEmail(@StringRes message: Int)
        fun invalidPassword(@StringRes message: Int)
    }

    interface Presenter : BasePresenter<UnitContract.View> {
        fun signIn(userNameOrEmail: String, password: String)
        fun isLoggedIn()
    }

}