package xyz.twbkg.stock.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import xyz.twbkg.stock.networking.AppScheduler
import xyz.twbkg.stock.networking.Scheduler
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
}