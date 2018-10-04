package xyz.twbkg.stock.data.model.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "unit")
@Parcelize
data class UnitMeasure(
        @ColumnInfo(name = "name")
        var name: String = "",
        @ColumnInfo(name = "description")
        var description: String = ""
):Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
