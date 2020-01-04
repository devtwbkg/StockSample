package xyz.twbkg.stock.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.twbkg.stock.data.source.local.category.CategoryDao
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.data.model.db.User
import xyz.twbkg.stock.data.source.local.unit.UnitDao
import xyz.twbkg.stock.data.source.local.user.UserDao


@Database(entities = [Category::class, UnitMeasure::class, User::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    abstract fun unitDao(): UnitDao

    abstract fun userDao(): UserDao
}