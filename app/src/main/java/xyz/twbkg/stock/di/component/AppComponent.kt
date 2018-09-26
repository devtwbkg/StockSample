package xyz.twbkg.stock.di.component

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import xyz.twbkg.stock.MainApp
import xyz.twbkg.stock.di.builder.ActivityBinder
import xyz.twbkg.stock.di.module.AppModule
import javax.inject.Singleton


@Singleton
@Component(
        modules = [AndroidSupportInjectionModule::class
            , AppModule::class
            , ActivityBinder::class
        ])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(application: Context): Builder

        fun build(): AppComponent
    }

    fun inject(mainApp: MainApp)
}