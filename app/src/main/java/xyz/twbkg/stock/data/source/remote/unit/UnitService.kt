package xyz.twbkg.stock.data.source.remote.unit

import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.*
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.data.model.request.UnitRequest
import xyz.twbkg.stock.data.model.response.FindResponse
import xyz.twbkg.stock.data.model.response.CreateResponse

interface UnitService {

    @GET("/api/v1/units")
    fun getAll(): Flowable<FindResponse<UnitMeasure>>

    @GET("/api/v1/units/{id}")
    fun getById(@Path("id") id: Int): Flowable<UnitMeasure>

    @GET("/api/v1/units/last")
    fun getLast(): Flowable<UnitMeasure>

    @POST("/api/v1/units/create")
    fun save(@Body unit: UnitRequest):  Flowable<CreateResponse<UnitMeasure>>

    @POST("/api/v1/units/create")
    fun saveAll(@Body units: List<UnitRequest>): Completable

    @PUT("/api/v1/units/update")
    fun update(@Body unit: UnitMeasure): Completable

    @PUT("/api/v1/units/update")
    fun updateAll(@Body units: List<UnitMeasure>): Completable

    @DELETE("/api/v1/units/delete")
    fun delete(@Path("id") id: Int): Completable

    @DELETE("/api/v1/units/delete")
    fun deleteAll(): Completable

}
