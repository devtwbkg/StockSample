package xyz.twbkg.stock.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import xyz.twbkg.stock.MainApp
import xyz.twbkg.stock.util.NetworkUtils
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    @Singleton
    fun providesNetworkUtil(context: Application): NetworkUtils = NetworkUtils(context)
}