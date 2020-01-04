package xyz.twbkg.stock.data.source.local.unit

import androidx.room.*
import io.reactivex.Flowable
import xyz.twbkg.stock.data.model.db.UnitMeasure

@Dao
interface UnitDao {
    /**
     * Select all unit from the unit table that haven't been archived
     *
     * @return all unarchived unit (items).
     */
    @Query("SELECT * FROM unit")
    fun findAll(): List<UnitMeasure>

    @Query("SELECT * FROM unit WHERE id = (SELECT max(id) FROM unit)")
    fun findLastId(): UnitMeasure

    /**
     * Select a unit (item) by id.
     *
     * @param id the unit (item) id.
     * @return the unit (item) with newsId.
     */
    @Query("SELECT * FROM unit WHERE id = :id")
    fun findById(id: Int): UnitMeasure

    /**
     * Insert unit (item) in the database. If the unit (item) already exists, replace it.
     *
     * @param unit to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(unit: UnitMeasure)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(unit: List<UnitMeasure>)

    /**
     * Delete a unit (item) by id.
     *
     * @return the number of unit (items) deleted. This should always be 1.
     */
    @Query("DELETE FROM unit WHERE id = :id")
    fun deleteById(id: Int): Int


    /**
     * Delete all unit (items).
     */
    @Query("DELETE FROM unit")
    fun delete()

    /**
     * Refresh unit table for a specific unit, keeping only archived unit (items)
     *
     * @return the number of unit deleted.
     */
    @Query("DELETE FROM unit")
    fun refresh(): Int

    @Update
    fun update(unit: UnitMeasure): Int
}