package xyz.twbkg.stock.data.source.remote.category

import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.*
import xyz.twbkg.stock.data.model.db.Category

interface CategoryService {

    @GET("/category")
    fun getAll(): Flowable<List<Category>>

    @GET("/category/{id}")
    fun getById(@Path("id") id: Int): Flowable<Category>

    @POST("/new")
    fun save(@Body unit: Category): Completable

    @PUT("/update")
    fun update(@Body unit: Category): Completable

    @PUT("/update")
    fun updateAll(@Body units: List<Category>): Completable

    @DELETE("/delete")
    fun delete(@Path("id") id: Int): Completable

    @DELETE("/delete")
    fun deleteAll(): Completable
}
