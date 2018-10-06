package xyz.twbkg.stock.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.twbkg.stock.ui.authen.signin.LoginFragment
import xyz.twbkg.stock.ui.authen.signin.LoginModule
import xyz.twbkg.stock.ui.category.addedit.AddEditCategoryFragment
import xyz.twbkg.stock.ui.category.addedit.AddEditCategoryPresenterModule
import xyz.twbkg.stock.ui.category.list.CategoryFragment
import xyz.twbkg.stock.ui.category.list.CategoryPresenterModule
import xyz.twbkg.stock.ui.main.MainFragment
import xyz.twbkg.stock.ui.unit.UnitFragment
import xyz.twbkg.stock.ui.unit.module.UnitPresenterModule

@Suppress("unused")
@Module
abstract class FragmentBinder {

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector(modules = [CategoryPresenterModule::class])
    abstract fun contributeCategoryFragment(): CategoryFragment

    @ContributesAndroidInjector(modules = [AddEditCategoryPresenterModule::class])
    abstract fun contributeAddEditFragment(): AddEditCategoryFragment

    @ContributesAndroidInjector(modules = [UnitPresenterModule::class])
    abstract fun contributeUnitFragment(): UnitFragment
}