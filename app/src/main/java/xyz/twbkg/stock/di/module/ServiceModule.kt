package xyz.twbkg.stock.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import xyz.twbkg.stock.data.source.remote.category.CategoryService
import xyz.twbkg.stock.di.scpe.ServiceScoped
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class ServiceModule {
    @Provides
    @Singleton
    fun provideCategoryApi(retrofit: Retrofit): CategoryService = retrofit.create(CategoryService::class.java)
}