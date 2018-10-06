package xyz.twbkg.stock.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import xyz.twbkg.stock.data.source.remote.authen.AuthenService
import xyz.twbkg.stock.data.source.remote.category.CategoryService
import xyz.twbkg.stock.data.source.remote.unit.UnitService
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class ServiceModule {

    @Provides
    @Singleton
    fun provideAuthenApi(retrofit: Retrofit) : AuthenService = retrofit.create(AuthenService::class.java)

    @Provides
    @Singleton
    fun provideCategoryApi(retrofit: Retrofit): CategoryService = retrofit.create(CategoryService::class.java)

    @Provides
    @Singleton
    fun provideUnitApi(retrofit: Retrofit): UnitService = retrofit.create(UnitService::class.java)

}