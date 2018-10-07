package xyz.twbkg.stock.ui.authen.signin

import dagger.Module
import dagger.Provides
import xyz.twbkg.stock.data.source.repository.impl.AuthenRepository
import xyz.twbkg.stock.data.source.repository.impl.UserRepository

@Module
class LoginModule {

    @Provides
    fun provideView(
            view: LoginFragment
    ): LoginContract.View = view

    @Provides
    fun providePresenter(
            view: LoginContract.View,
            authenRepository: AuthenRepository,
            userRepository: UserRepository
    ): LoginContract.Presenter =
            LoginPresenter(view, authenRepository, userRepository)
}