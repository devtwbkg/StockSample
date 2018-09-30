package xyz.twbkg.stock.data.source.remote.category

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.data.source.local.category.CategoryDao
import xyz.twbkg.stock.data.source.repository.category.CategoryDataSource
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRemoteDataSource @Inject constructor(
        var service: CategoryService
) : CategoryDataSource {
    override fun findLastId(): Flowable<Category> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): Flowable<List<Category>> = service.getCategories()

    override fun findById(id: Int): Flowable<Category> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(model: Category): Completable = Completable.fromAction {
        service.save(model)
    }

    override fun saveAll(model: List<Category>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(model: Category): Completable= Completable.fromAction {
        service.update(model)
    }


    override fun refreshData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAll(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: Int): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}