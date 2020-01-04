package xyz.twbkg.stock

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import xyz.twbkg.stock.data.model.LoggedUser
import xyz.twbkg.stock.data.model.request.RequestHeader
import xyz.twbkg.stock.di.component.AppInjector
import javax.inject.Inject
import javax.annotation.Nonnull
import xyz.twbkg.stock.data.source.repository.impl.UserRepository
import io.reactivex.schedulers.Schedulers.computation
import xyz.twbkg.stock.data.model.db.User


class MainApp : Application()
        , HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    @Inject
    lateinit var loggedUser: LoggedUser
    @Inject
    lateinit var requestHeader: RequestHeader
    @Inject
    lateinit var userRepository: UserRepository


    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
        initializeApplication()
    }

    private fun initializeApplication() {
        //load the current user into the system
        val token = userRepository
                .findLastSignIn()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //load the current access token into all requests
                    token ->
                    if (token.isNotEmpty()) {
                        requestHeader.loggedUser.accessToken = token
                        loggedUser.accessToken = token
                    }
                }, { error -> Timber.e("initializeApplication error $error") })

    }

    companion object {
        var token: String = ""
    }
}