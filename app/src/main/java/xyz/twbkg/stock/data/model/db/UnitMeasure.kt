package xyz.twbkg.stock.data.model.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "unit")
@Parcelize
data class UnitMeasure(
        @SerializedName("name")
        @ColumnInfo(name = "name")
        var name: String = "",
        @SerializedName("no")
        @ColumnInfo(name = "no")
        var no: String = "",
        @PrimaryKey
        @SerializedName("id")
        @ColumnInfo(name = "id")
        var id: Int = 0
) : Parcelable {

}
