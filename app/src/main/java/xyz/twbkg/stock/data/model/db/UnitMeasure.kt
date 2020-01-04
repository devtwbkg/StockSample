package xyz.twbkg.stock.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
