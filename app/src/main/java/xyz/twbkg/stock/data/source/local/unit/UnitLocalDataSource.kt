package xyz.twbkg.stock.data.source.local.unit

import io.reactivex.Completable
import io.reactivex.Flowable
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.data.source.repository.UnitDataSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UnitLocalDataSource @Inject constructor(
        var dao: UnitDao
) : UnitDataSource.LocalDataSource {
    override fun getLastLocal(): Flowable<UnitMeasure> {
        return Flowable.fromCallable { dao.findLastId() }
    }

    override fun getAllLocal(): Flowable<List<UnitMeasure>> {
        return Flowable.fromCallable { dao.findAll() }
    }

    override fun getByIdLocal(id: Int): Flowable<UnitMeasure> {
        return Flowable.fromCallable { dao.findById(id) }
    }

    override fun saveLocal(model: UnitMeasure): Completable {
        return Completable.fromAction { dao.insert(model) }
    }

    override fun saveAllLocal(models: List<UnitMeasure>): Completable {
        return Completable.fromAction { dao.insertAll(models) }
    }

    override fun updateLocal(model: UnitMeasure): Completable {
        return Completable.fromAction { dao.update(model) }
    }

    override fun deleteAllLocal(): Completable {
        return Completable.fromAction { dao.delete() }
    }

    override fun deleteLocal(id: Int): Completable {
        return Completable.fromAction { dao.deleteById(id) }
    }
}