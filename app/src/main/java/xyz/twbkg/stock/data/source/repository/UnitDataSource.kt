package xyz.twbkg.stock.data.source.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.data.model.request.UnitRequest
import xyz.twbkg.stock.data.model.response.FindResponse
import xyz.twbkg.stock.data.model.response.CreateResponse
import xyz.twbkg.stock.data.source.local.common.BaseDataSource

interface UnitDataSource : BaseDataSource<UnitMeasure> {


    fun save(model: UnitRequest):  Flowable<UnitMeasure>

    interface LocalDataSource {

        fun getLastLocal(): Flowable<UnitMeasure>

        fun getAllLocal(): Flowable<List<UnitMeasure>>

        fun getByIdLocal(id: Int): Flowable<UnitMeasure>

        fun saveLocal(model: UnitMeasure): Completable

        fun saveAllLocal(models: List<UnitMeasure>): Completable

        fun updateLocal(model: UnitMeasure): Completable

        fun deleteAllLocal(): Completable

        fun deleteLocal(id: Int): Completable
    }

    interface RemoteDataSource {

        fun getLastRemote(): Flowable<UnitMeasure>

        fun getAllRemote(): Flowable<FindResponse<UnitMeasure>>

        fun getByIdRemote(id: Int): Flowable<UnitMeasure>

        fun saveRemote(model: UnitRequest): Flowable<CreateResponse<UnitMeasure>>

        fun saveAllRemote(models: List<UnitRequest>): Completable

        fun updateRemote(model: UnitMeasure): Completable

        fun deleteAllRemote(): Completable

        fun deleteRemote(id: Int): Completable
    }
}