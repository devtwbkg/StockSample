package xyz.twbkg.stock.data.model.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user")
data class User(
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: Int = 1,
        @ColumnInfo(name = "name")
        var name: String? = "",
        @ColumnInfo(name = "username")
        var username: String? = "",
        @ColumnInfo(name = "email")
        var email: String? = "",
        @ColumnInfo(name = "token")
        var token: String = ""
) {
}