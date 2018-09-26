package xyz.twbkg.stock.di.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import xyz.twbkg.stock.data.source.database.AppDataBase
import xyz.twbkg.stock.data.source.local.category.CategoryDao
import javax.inject.Singleton

@Suppress("unused")
@Module
class StorageModule {
    @Provides
    @Singleton
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }


    @Singleton
    @Provides
    fun provideDb(app: Application): AppDataBase = Room
            .databaseBuilder(app, AppDataBase::class.java, "stock.db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideCategoryDao(db: AppDataBase): CategoryDao {
        return db.categoryDao()
    }
}