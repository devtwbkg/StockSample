package xyz.twbkg.stock.data.source.remote.unit

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.*
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.data.model.response.CategoryResponse

interface UnitService {

    @GET("/unit")
    fun getAll(): Flowable<List<UnitMeasure>>

    @GET("/unit/{id}")
    fun getById(@Path("id") id: Int): Flowable<UnitMeasure>

    @POST("/new")
    fun save(@Body unit: UnitMeasure): Completable

    @PUT("/update")
    fun update(@Body unit: UnitMeasure): Completable

    @PUT("/update")
    fun updateAll(@Body units: List<UnitMeasure>): Completable

    @DELETE("/delete")
    fun delete(@Path("id") id: Int): Completable

    @DELETE("/delete")
    fun deleteAll(): Completable

}
