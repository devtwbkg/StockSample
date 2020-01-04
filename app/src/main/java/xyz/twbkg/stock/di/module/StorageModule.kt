package xyz.twbkg.stock.di.module

import android.app.Application
import androidx.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import xyz.twbkg.stock.data.source.database.AppDataBase
import xyz.twbkg.stock.data.source.local.category.CategoryDao
import xyz.twbkg.stock.data.source.local.unit.UnitDao
import xyz.twbkg.stock.data.source.local.user.UserDao
import javax.inject.Singleton

@Module
class StorageModule {
    @Provides
    @Singleton
    fun providesSharedPreferences(context: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDataBase = Room
            .databaseBuilder(app, AppDataBase::class.java, "mini_stock.db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideCategoryDao(db: AppDataBase): CategoryDao = db.categoryDao()

    @Singleton
    @Provides
    fun provideUnitDao(db: AppDataBase): UnitDao = db.unitDao()

    @Singleton
    @Provides
    fun provideUserDao(db: AppDataBase): UserDao = db.userDao()
}