package xyz.twbkg.stock.data.source.local.category

import io.reactivex.Completable
import io.reactivex.Flowable
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.data.model.response.FindResponse
import xyz.twbkg.stock.data.source.repository.CategoryDataSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CategoryLocalDataSource @Inject constructor(
        var dao: CategoryDao
) : CategoryDataSource {

    override fun findAll(): Flowable<List<Category>> = Flowable.fromCallable {
        dao.findAll()
    }

    override fun findFromServer(): Flowable<FindResponse<Category>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findLastId(): Flowable<Category> = Flowable.fromCallable {
        dao.findLastId()
    }

    override fun findById(id: Int): Flowable<Category> = Flowable.fromCallable {
        dao.findById(id)
    }

    override fun save(model: Category): Completable = Completable.fromAction {
        dao.insert(model)
    }

    override fun saveAll(models: List<Category>): Completable = Completable.fromAction {
        dao.insertAll(models)
    }

    override fun update(model: Category): Completable = Completable.fromAction {
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