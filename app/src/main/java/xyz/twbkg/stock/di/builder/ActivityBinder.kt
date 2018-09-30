package xyz.twbkg.stock.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.twbkg.stock.ui.category.AddEditCategoryActivity
import xyz.twbkg.stock.ui.category.CategoryActivity
import xyz.twbkg.stock.ui.main.MainActivity

@Module
abstract class ActivityBinder {

    @ContributesAndroidInjector(modules = [FragmentBinder::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [CategoryBinderModule::class])
    abstract fun bindCategoryActivity(): CategoryActivity

    @ContributesAndroidInjector(modules = [CategoryBinderModule::class])
    abstract fun bindAddEditCategoryActivity(): AddEditCategoryActivity
}