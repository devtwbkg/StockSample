package xyz.twbkg.stock.ui.category.list

import dagger.Module
import dagger.Provides
import xyz.twbkg.stock.data.source.repository.impl.CategoryRepository


@Module class CategoryPresenterModule {

    @Provides
    fun provideView(
            view: CategoryFragment
    ): CategoryContract.View = view

    @Provides
    fun providePresenter(
            view: CategoryContract.View,
            categoryRepository: CategoryRepository
    ): CategoryContract.Presenter =
            CategoryPresenter(view, categoryRepository)
}