package xyz.twbkg.stock.data.source.local.user

import androidx.room.*
import xyz.twbkg.stock.data.model.db.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(user: User)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(user: User)

    @Query("SELECT * FROM user WHERE token = :token LIMIT 1")
    fun findByToken(token: String): User

    @Query("SELECT token FROM user WHERE id=1 LIMIT 1")
    fun findLastSignIn(): String

    @Query("SELECT * FROM user LIMIT 1")
    fun findUserLogin(): User

    @Query("DELETE FROM user")
    fun deleteAll()
}