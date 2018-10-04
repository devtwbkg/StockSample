package xyz.twbkg.stock.data.source.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import xyz.twbkg.stock.data.source.local.category.CategoryDao
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.data.source.local.unit.UnitDao


@Database(entities = [Category::class, UnitMeasure::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    abstract fun unitDao(): UnitDao
}