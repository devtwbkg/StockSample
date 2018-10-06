package xyz.twbkg.stock.data.source.remote.unit

import io.reactivex.Completable
import io.reactivex.Flowable
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.data.source.repository.UnitDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnitRemoteDataSource @Inject constructor(
        var service: UnitService
) : UnitDataSource {

    override fun findLastId(): Flowable<UnitMeasure> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): Flowable<List<UnitMeasure>> = service.getAll()

    override fun findById(id: Int): Flowable<UnitMeasure> = service.getById(id)

    override fun save(model: UnitMeasure): Completable = Completable.fromAction {
        service.save(model)
    }

    override fun saveAll(models: List<UnitMeasure>): Completable = Completable.fromAction {
        service.updateAll(models)
    }

    override fun update(model: UnitMeasure): Completable = Completable.fromAction {
        service.update(model)
    }

    override fun refreshData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAll(): Completable = Completable.fromAction {
        service.deleteAll()
    }

    override fun delete(id: Int): Completable = Completable.fromAction {
        service.delete(id)
    }
}