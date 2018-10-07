package xyz.twbkg.stock.data.source.local.auth

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import xyz.twbkg.stock.data.model.db.User
import xyz.twbkg.stock.data.model.response.SigInResponse

@Dao
interface AuthDao {

}