package xyz.twbkg.stock.data.source.repository.impl

import io.reactivex.Completable
import io.reactivex.Flowable
import timber.log.Timber
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.data.source.repository.UnitDataSource
import xyz.twbkg.stock.data.source.scope.Local
import xyz.twbkg.stock.data.source.scope.Remote
import javax.inject.Inject
import javax.inject.Singleton
import android.support.annotation.NonNull
import kotlin.collections.LinkedHashMap


@Singleton
class UnitRepository @Inject constructor(
        @Remote val remoteDataSource: UnitDataSource,
        @Local val localDataSource: UnitDataSource
) : UnitDataSource {
    /**
     * This variable has public visibility so it can be accessed from tests.
     */
    private lateinit var cachedItem: LinkedHashMap<Int, UnitMeasure>

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    var cacheIsDirty = false


    override fun findAll(): Flowable<List<UnitMeasure>> {
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
            Flowable.concat<List<UnitMeasure>>(localTasks, remoteTasks)
                    .filter { tasks ->
                        !tasks.isEmpty()
                    }
                    .firstOrError()
                    .toFlowable()
        }
    }

    override fun findLastId(): Flowable<UnitMeasure> = localDataSource.findLastId()

    override fun findById(id: Int): Flowable<UnitMeasure> {
        val cachedUnit = getWithId(id)

        // Respond immediately with cache if available
        if (cachedUnit != null) {
            return Flowable.just(cachedUnit)
        }

        // Load from server/persisted if needed.

        //Do in memory cache update to keep the app UI up to date
        if (!::cachedItem.isInitialized || cachedItem == null) {
            cachedItem = LinkedHashMap()
        }

        val local = getWithIdFromLocalRepository(id)
        val remote = remoteDataSource
                .findById(id)
                .doOnNext { item ->
                    localDataSource.save(item)
                    cachedItem.put(item.id, item)
                }

        return Flowable.concat(local, remote)
                .firstElement()
                .toFlowable()
    }

    override fun save(model: UnitMeasure): Completable {
        return remoteDataSource.save(model)
                .andThen(localDataSource.save(model))
    }

    override fun saveAll(models: List<UnitMeasure>): Completable {
        return remoteDataSource.saveAll(models)
                .andThen(localDataSource.saveAll(models))
    }

    override fun update(model: UnitMeasure): Completable {
        return remoteDataSource.update(model)
                .andThen(localDataSource.update(model))
    }

    override fun refreshData() {
        cacheIsDirty = true
    }

    override fun deleteAll(): Completable {
        return remoteDataSource.deleteAll()
                .andThen(localDataSource.deleteAll())
    }

    override fun delete(id: Int): Completable {
        return remoteDataSource.delete(id)
                .andThen(localDataSource.delete(id))
    }


    private fun getAndCacheLocalTasks(): Flowable<List<UnitMeasure>> {
        return localDataSource
                .findAll()
                .flatMap { tasks ->
                    Timber.i("tasks getAndCacheLocalTasks ${tasks.size}")
                    Flowable.fromIterable(tasks)
                            .doOnNext { task -> cachedItem.put(task.id, task) }
                            .toList()
                            .toFlowable()
                }
    }

    private fun getAndSaveRemoteTasks(): Flowable<List<UnitMeasure>> {
        return remoteDataSource
                .findAll()
                .flatMap { tasks ->
                    Timber.i("tasks getAndSaveRemoteTasks ${tasks.size}")
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
                .doOnComplete { cacheIsDirty = false }
    }

    private fun getWithId(id: Int): UnitMeasure? {
        checkNotNull(id)
        return if (!::cachedItem.isInitialized || cachedItem.isEmpty()) {
            null
        } else {
            cachedItem[id]
        }
    }

    private fun getWithIdFromLocalRepository(@NonNull id: Int): Flowable<UnitMeasure> {
        return localDataSource
                .findById(id)
                .doOnNext { taskOptional ->
                    cachedItem.put(id, taskOptional)
                }
                .firstElement().toFlowable()
    }
}