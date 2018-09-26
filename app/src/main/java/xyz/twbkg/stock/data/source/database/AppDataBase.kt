package xyz.twbkg.stock.data.source.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import xyz.twbkg.stock.data.source.local.category.CategoryDao
import xyz.twbkg.stock.data.model.db.Category

@Database(entities = [Category::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

//    abstract fun postDao(): PostDao

    abstract fun categoryDao(): CategoryDao

//    abstract fun unitDao(): UnitDao
}