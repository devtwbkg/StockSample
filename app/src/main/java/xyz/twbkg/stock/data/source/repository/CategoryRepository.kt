package xyz.twbkg.stock.data.source.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.intellij.lang.annotations.Flow
import timber.log.Timber
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.data.source.repository.category.CategoryDataSource
import xyz.twbkg.stock.data.source.scope.Local
import xyz.twbkg.stock.data.source.scope.Remote
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
        @Remote val remoteDataSource: CategoryDataSource,
        @Local val localDataSource: CategoryDataSource
) : CategoryDataSource {


    /**
     * This variable has public visibility so it can be accessed from tests.
     */
    lateinit var cachedItem: LinkedHashMap<Int, Category>

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    var cacheIsDirty = false


    override fun findAll(): Flowable<List<Category>> {
        // Respond immediately with cache if available and not dirty
        if (::cachedItem.isInitialized && !cacheIsDirty) {
            return Flowable.fromIterable(cachedItem.values).toList().toFlowable()
        } else if (!::cachedItem.isInitialized) {
            cachedItem = LinkedHashMap()
        }

        val remoteTasks = getAndSaveRemoteTasks()


        return if (cacheIsDirty) {
            remoteTasks
        } else {
            // Query the local storage if available. If not, query the network.
            val localTasks = getAndCacheLocalTasks()
            Flowable.concat<List<Category>>(localTasks, remoteTasks)
                    .filter { tasks -> !tasks.isEmpty() }
                    .firstOrError()
                    .toFlowable()
        }
    }

    override fun findLastId(): Flowable<Category> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(id: Int): Flowable<Category> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(model: Category): Completable {
        return remoteDataSource.save(model)
                .andThen(localDataSource.save(model))
    }

    override fun saveAll(model: List<Category>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(model: Category): Completable {
        return remoteDataSource.update(model)
                .andThen(localDataSource.update(model))
    }

    override fun refreshData() {
        cacheIsDirty = true
    }

    override fun deleteAll(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: Int): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun getAndCacheLocalTasks(): Flowable<List<Category>> {
        return localDataSource
                .findAll()
                .flatMap { tasks ->
                    Flowable.fromIterable(tasks)
                            .doOnNext { task -> cachedItem.put(task.id, task) }
                            .toList()
                            .toFlowable()
                }
    }

    private fun getAndSaveRemoteTasks(): Flowable<List<Category>> {
        return remoteDataSource
                .findAll()
                .flatMap { tasks ->
                    Timber.i("tasks $tasks")
                    Flowable
                            .fromIterable(tasks)
                            .doOnNext { task ->
                                Timber.i("task result $task")
                                localDataSource.save(task)
                                cachedItem.put(task.id, task)
                            }
                            .toList()
                            .toFlowable()
                }
//                .doOnNext { items ->
//                    Timber.i("task result $items")
//                    localDataSource.saveTasks(items)
//                    for (item in items) {
//                        cachedItem.put(item.id, item)
//                    }
//                }
                .doOnComplete { cacheIsDirty = false }
    }
}