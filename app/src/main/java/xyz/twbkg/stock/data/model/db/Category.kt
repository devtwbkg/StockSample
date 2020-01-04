package xyz.twbkg.stock.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "category")
@Parcelize
data class Category(
        @ColumnInfo(name = "name")
        var name: String = "",
        @ColumnInfo(name = "no")
        var description: String = ""
):Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
