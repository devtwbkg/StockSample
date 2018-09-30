package xyz.twbkg.stock.data.source.local.category

import android.arch.persistence.room.*
import io.reactivex.Flowable
import xyz.twbkg.stock.data.model.db.Category

@Dao
interface CategoryDao {
    /**
     * Select all category from the category table that haven't been archived
     *
     * @return all unarchived category (items).
     */
    @Query("SELECT * FROM category")
    fun findAll(): Flowable<List<Category>>

    @Query("SELECT * FROM category WHERE id = (SELECT max(id) FROM CATEGORY)")
    fun findLastId(): Category

    /**
     * Select a category (item) by id.
     *
     * @param id the category (item) id.
     * @return the category (item) with newsId.
     */
    @Query("SELECT * FROM category WHERE id = :id")
    fun findById(id: Int): Category

    /**
     * Insert category (item) in the database. If the category (item) already exists, replace it.
     *
     * @param category to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(category: Category)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(category: List<Category>)

    /**
     * Delete a category (item) by id.
     *
     * @return the number of category (items) deleted. This should always be 1.
     */
    @Query("DELETE FROM category WHERE id = :id")
    fun deleteById(id: Int): Int


    /**
     * Delete all category (items).
     */
    @Query("DELETE FROM category")
    fun delete()

    /**
     * Refresh category table for a specific category, keeping only archived category (items)
     *
     * @return the number of category deleted.
     */
    @Query("DELETE FROM category")
    fun refresh(): Int

    @Update
    fun update(category: Category): Int
}