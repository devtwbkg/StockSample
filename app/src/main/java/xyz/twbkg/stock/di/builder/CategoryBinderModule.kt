package xyz.twbkg.stock.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.twbkg.stock.ui.category.addedit.AddEditCategoryFragment
import xyz.twbkg.stock.ui.category.addedit.AddEditCategoryPresenterModule
import xyz.twbkg.stock.ui.category.list.CategoryFragment
import xyz.twbkg.stock.ui.category.list.CategoryPresenterModule

@Module
abstract class CategoryBinderModule {
    @ContributesAndroidInjector(modules = [CategoryPresenterModule::class])
    abstract fun contributeCategoryFragment(): CategoryFragment

    @ContributesAndroidInjector(modules = [AddEditCategoryPresenterModule::class])
    abstract fun contributeAddEditFragment(): AddEditCategoryFragment
}