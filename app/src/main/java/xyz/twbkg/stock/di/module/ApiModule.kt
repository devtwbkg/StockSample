package xyz.twbkg.stock.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import xyz.twbkg.stock.data.source.remote.category.CategoryService
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideCategoryApi(retrofit: Retrofit): CategoryService = retrofit.create(CategoryService::class.java)
}