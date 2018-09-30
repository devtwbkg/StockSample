package xyz.twbkg.stock.di.module

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import xyz.twbkg.stock.data.source.local.category.CategoryDao
import xyz.twbkg.stock.data.source.local.category.CategoryLocalDataSource
import xyz.twbkg.stock.data.source.remote.category.CategoryRemoteDataSource
import xyz.twbkg.stock.data.source.remote.category.CategoryService
import xyz.twbkg.stock.data.source.repository.CategoryRepo
import xyz.twbkg.stock.data.source.repository.category.CategoryDataSource
import xyz.twbkg.stock.data.source.scope.Local
import xyz.twbkg.stock.data.source.scope.Remote
import javax.inject.Singleton

@Module
abstract class ContractModule {

    @Singleton
    @Binds
    @Local
    abstract fun provideCategoryLocalDataSource(dataSource: CategoryLocalDataSource): CategoryDataSource

    @Singleton
    @Binds
    @Remote
    abstract fun provideCategoryRemoteDataSource(dataSource: CategoryRemoteDataSource): CategoryDataSource

}