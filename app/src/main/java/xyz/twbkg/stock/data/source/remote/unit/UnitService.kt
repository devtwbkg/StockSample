package xyz.twbkg.stock.data.source.remote.unit

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.*
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.data.model.response.CategoryResponse

interface UnitService {

    @GET("/unit")
    fun getUnit(): Flowable<List<UnitMeasure>>

    @GET("/unit")
    fun getCategoryV2(): Observable<CategoryResponse>

    @POST("unit/new")
    fun save(@Body unit: UnitMeasure): Completable

    @PUT("/update")
    fun update(@Body unit: UnitMeasure): Completable
}
