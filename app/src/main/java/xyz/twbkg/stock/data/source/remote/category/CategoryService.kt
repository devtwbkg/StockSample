package xyz.twbkg.stock.data.source.remote.category

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.data.model.response.CategoryResponse

interface CategoryService {
    @GET("/category")
    fun getCategory(): Flowable<CategoryResponse>

    @GET("/category")
    fun getCategories(): Flowable<List<Category>>

    @GET("/category")
    fun getCategoryV2(): Observable<CategoryResponse>

    @POST("category/new")
    fun save(@Body category: Category): Completable

    @PUT("/update")
    @Multipart
    fun update(@Body category: Category): Completable
}
