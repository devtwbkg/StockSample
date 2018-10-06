package xyz.twbkg.stock.ui.authen.signin

import dagger.Module
import dagger.Provides
import xyz.twbkg.stock.data.source.repository.impl.AuthenRepository

@Module
class LoginModule {

    @Provides
    fun provideView(
            view: LoginFragment
    ): LoginContract.View = view

    @Provides
    fun providePresenter(
            view: LoginContract.View,
            authenRepository: AuthenRepository
    ): LoginContract.Presenter =
            LoginPresenter(view, authenRepository)
}