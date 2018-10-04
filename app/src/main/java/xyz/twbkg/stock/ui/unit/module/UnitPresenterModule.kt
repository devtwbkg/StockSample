package xyz.twbkg.stock.ui.unit.module

import dagger.Module
import dagger.Provides
import xyz.twbkg.stock.data.source.repository.UnitRepository
import xyz.twbkg.stock.ui.unit.UnitContract
import xyz.twbkg.stock.ui.unit.UnitFragment
import xyz.twbkg.stock.ui.unit.UnitPresenter

@Module
class UnitPresenterModule {

    @Provides
    fun provideView(
            view: UnitFragment
    ): UnitContract.View = view

    @Provides
    fun providePresenter(
            view: UnitContract.View,
            unitRepository: UnitRepository
    ): UnitContract.Presenter = UnitPresenter(view, unitRepository)

}