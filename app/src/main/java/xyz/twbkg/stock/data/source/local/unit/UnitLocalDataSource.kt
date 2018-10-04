package xyz.twbkg.stock.data.source.local.unit

import io.reactivex.Completable
import io.reactivex.Flowable
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.data.source.repository.category.UnitDataSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UnitLocalDataSource @Inject constructor(
        var dao: UnitDao
) : UnitDataSource {


    override fun findAll(): Flowable<List<UnitMeasure>> = dao.findAll()

    override fun findLastId(): Flowable<UnitMeasure> {
        return Flowable.fromCallable { dao.findLastId() }
    }

    override fun findById(id: Int): Flowable<UnitMeasure> {
        return Flowable.fromCallable { dao.findById(id) }
    }

    override fun save(model: UnitMeasure): Completable {
        return Completable.fromAction {
            dao.insert(model)
        }
    }


    override fun saveAll(model: List<UnitMeasure>): Completable = Completable.fromAction {
        dao.insertAll(model)
    }

    override fun update(model: UnitMeasure): Completable = Completable.fromAction {
        dao.update(model)
    }

    override fun refreshData() {
    }

    override fun deleteAll(): Completable = Completable.fromAction {
        dao.delete()
    }

    override fun delete(id: Int): Completable = Completable.fromAction {
        dao.deleteById(id)
    }

}