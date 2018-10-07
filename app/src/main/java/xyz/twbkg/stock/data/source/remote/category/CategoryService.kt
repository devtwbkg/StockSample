package xyz.twbkg.stock.data.source.remote.category

import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.*
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.data.model.response.FindResponse

interface CategoryService {

    @GET("/api/v1/categories")
    fun getAll(): Flowable<FindResponse<Category>>

    @GET("/api/v1/categories/{id}")
    fun getById(@Path("id") id: Int): Flowable<Category>

    @POST("/api/v1/categories/create")
    fun save(@Body unit: Category): Completable

    @PUT("/api/v1/categories/update")
    fun update(@Body unit: Category): Completable

    @PUT("/api/v1/categories/update")
    fun updateAll(@Body units: List<Category>): Completable

    @DELETE("/api/v1/categories/delete")
    fun delete(@Path("id") id: Int): Completable

    @DELETE("/api/v1/categories/delete")
    fun deleteAll(): Completable
}
