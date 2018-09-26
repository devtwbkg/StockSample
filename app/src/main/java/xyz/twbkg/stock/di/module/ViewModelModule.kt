package xyz.twbkg.stock.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import xyz.twbkg.stock.di.common.ViewModelFactory
import xyz.twbkg.stock.di.common.ViewModelKey
import xyz.twbkg.stock.ui.main.MainViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    //    @Binds
//    @IntoMap
//    @ViewModelKey(PostListViewModel::class)
//    abstract fun bindPostListViewModel(postListViewModel: PostListViewModel): ViewModel
//
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    // add other view model
}