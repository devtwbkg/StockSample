package xyz.twbkg.stock.data.source.remote.unit

import io.reactivex.Completable
import io.reactivex.Flowable
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.data.model.request.UnitRequest
import xyz.twbkg.stock.data.model.response.FindResponse
import xyz.twbkg.stock.data.model.response.CreateResponse
import xyz.twbkg.stock.data.source.repository.UnitDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnitRemoteDataSource @Inject constructor(
        var service: UnitService
) : UnitDataSource.RemoteDataSource {

    override fun getLastRemote(): Flowable<UnitMeasure> {
        return service.getLast()
    }

    override fun getAllRemote(): Flowable<FindResponse<UnitMeasure>> {
        return service.getAll()
    }

    override fun getByIdRemote(id: Int): Flowable<UnitMeasure> {
        return service.getById(id)
    }

    override fun saveRemote(model: UnitRequest): Flowable<CreateResponse<UnitMeasure>> {
        return service.save(model)
    }

    override fun saveAllRemote(models: List<UnitRequest>): Completable {
        return service.saveAll(models)
    }

    override fun updateRemote(model: UnitMeasure): Completable {
        return service.update(model)
    }

    override fun deleteAllRemote(): Completable {
        return service.deleteAll()
    }

    override fun deleteRemote(id: Int): Completable {
        return service.delete(id)
    }

}