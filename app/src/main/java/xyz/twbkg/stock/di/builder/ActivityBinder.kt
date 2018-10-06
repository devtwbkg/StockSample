package xyz.twbkg.stock.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.twbkg.stock.ui.authen.AuthenActivity
import xyz.twbkg.stock.ui.category.AddEditCategoryActivity
import xyz.twbkg.stock.ui.category.CategoryActivity
import xyz.twbkg.stock.ui.main.MainActivity
import xyz.twbkg.stock.ui.unit.UnitActivity

@Module
abstract class ActivityBinder {

    @ContributesAndroidInjector(modules = [FragmentBinder::class])
    abstract fun bindAuthenActivity(): AuthenActivity

    @ContributesAndroidInjector(modules = [FragmentBinder::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentBinder::class])
    abstract fun bindCategoryActivity(): CategoryActivity

    @ContributesAndroidInjector(modules = [FragmentBinder::class])
    abstract fun bindUnitActivity(): UnitActivity

    @ContributesAndroidInjector(modules = [FragmentBinder::class])
    abstract fun bindAddEditCategoryActivity(): AddEditCategoryActivity
}