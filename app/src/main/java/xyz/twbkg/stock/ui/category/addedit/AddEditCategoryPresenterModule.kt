package xyz.twbkg.stock.ui.category.addedit

import dagger.Module
import dagger.Provides
import xyz.twbkg.stock.data.source.repository.CategoryRepository

@Module
class AddEditCategoryPresenterModule {

    @Provides
    fun provideView(
            view: AddEditCategoryFragment
    ): AddEditCategoryContract.View = view

    @Provides
    fun providePresenter(
            view: AddEditCategoryContract.View,
            categoryRepository: CategoryRepository
    ): AddEditCategoryContract.Presenter =
            AddEditCategoryPresenter(view, categoryRepository)

}