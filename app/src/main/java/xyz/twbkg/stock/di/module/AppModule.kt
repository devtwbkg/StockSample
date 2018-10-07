package xyz.twbkg.stock.di.module

import android.app.Application
import android.content.Context
import android.view.View
import dagger.Module
import dagger.Provides
import xyz.twbkg.stock.networking.AppScheduler
import xyz.twbkg.stock.networking.Scheduler
import xyz.twbkg.stock.ui.common.Resolution
import xyz.twbkg.stock.ui.common.ResolutionByCase
import xyz.twbkg.stock.ui.common.UIResolution
import xyz.twbkg.stock.ui.common.UIResolver
import javax.inject.Singleton

@Module(includes = [RepositoryModule::class, UtilsModule::class, ServiceModule::class, StorageModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun scheduler(): Scheduler {
        return AppScheduler()
    }

    @Provides
    @Singleton
    fun provideUIResolver(): UIResolver {
        return UIResolver()
    }

    @Provides
    @Singleton
    fun provideResolution(uiResolver: UIResolver): Resolution {
        return UIResolution(uiResolver)
    }

}