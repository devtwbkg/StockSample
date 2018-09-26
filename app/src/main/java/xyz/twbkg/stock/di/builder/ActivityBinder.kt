package xyz.twbkg.stock.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.twbkg.stock.ui.main.MainActivity

@Module
abstract class ActivityBinder {

    @ContributesAndroidInjector(modules = [FragmentBinder::class])
    abstract fun bindMainActivity(): MainActivity
}