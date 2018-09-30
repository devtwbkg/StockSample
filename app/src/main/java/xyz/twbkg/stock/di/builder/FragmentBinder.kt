package xyz.twbkg.stock.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.twbkg.stock.ui.main.MainFragment

@Suppress("unused")
@Module(includes = [CategoryBinderModule::class])
abstract class FragmentBinder {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

}