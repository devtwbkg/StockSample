package xyz.twbkg.stock.ui.category.list

import dagger.Module
import dagger.Provides
import xyz.twbkg.stock.data.source.repository.CategoryRepo


@Module class CategoryPresenterModule {

    @Provides
    fun provideView(
            view: CategoryFragment
    ): CategoryContract.View = view

    @Provides
    fun providePresenter(
            view: CategoryContract.View,
            categoryRepo: CategoryRepo
    ): CategoryContract.Presenter =
            CategoryPresenter(view, categoryRepo)
}