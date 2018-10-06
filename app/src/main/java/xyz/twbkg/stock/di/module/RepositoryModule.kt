package xyz.twbkg.stock.di.module

import dagger.Module
import xyz.twbkg.stock.data.source.scope.Local
import dagger.Provides
import xyz.twbkg.stock.data.source.local.category.CategoryDao
import xyz.twbkg.stock.data.source.local.category.CategoryLocalDataSource
import xyz.twbkg.stock.data.source.local.unit.UnitDao
import xyz.twbkg.stock.data.source.local.unit.UnitLocalDataSource
import xyz.twbkg.stock.data.source.remote.authen.AuthenRemoteDataSource
import xyz.twbkg.stock.data.source.remote.authen.AuthenService
import xyz.twbkg.stock.data.source.remote.category.CategoryRemoteDataSource
import xyz.twbkg.stock.data.source.remote.category.CategoryService
import xyz.twbkg.stock.data.source.remote.unit.UnitRemoteDataSource
import xyz.twbkg.stock.data.source.remote.unit.UnitService
import xyz.twbkg.stock.data.source.repository.AuthenticationDataSource
import xyz.twbkg.stock.data.source.repository.CategoryDataSource
import xyz.twbkg.stock.data.source.repository.UnitDataSource
import xyz.twbkg.stock.data.source.scope.Remote
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Singleton
    @Provides
    @Remote
    fun provideAuthenticationRemoteDataSource(service: AuthenService): AuthenticationDataSource {
        return AuthenRemoteDataSource(service)
    }

    @Singleton
    @Provides
    @Local
    fun provideCategoryLocalDataSource(dao: CategoryDao): CategoryDataSource {
        return CategoryLocalDataSource(dao)
    }

    @Singleton
    @Provides
    @Remote
    fun provideCategoryRemoteDataSource(service: CategoryService): CategoryDataSource {
        return CategoryRemoteDataSource(service)
    }

    @Singleton
    @Provides
    @Local
    fun provideUnitLocalDataSource(dao: UnitDao): UnitDataSource {
        return UnitLocalDataSource(dao)
    }

    @Singleton
    @Provides
    @Remote
    fun provideUnitRemoteDataSource(service: UnitService): UnitDataSource {
        return UnitRemoteDataSource(service)
    }
}