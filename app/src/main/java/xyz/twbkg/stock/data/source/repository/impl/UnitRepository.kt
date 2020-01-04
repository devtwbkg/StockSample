package xyz.twbkg.stock.data.source.repository.impl

import io.reactivex.Completable
import io.reactivex.Flowable
import timber.log.Timber
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.data.source.repository.UnitDataSource
import xyz.twbkg.stock.data.source.scope.Local
import xyz.twbkg.stock.data.source.scope.Remote
import javax.inject.Inject
import androidx.annotation.NonNull
import xyz.twbkg.stock.data.model.request.UnitRequest
import xyz.twbkg.stock.data.model.response.FindResponse
import javax.inject.Singleton
import kotlin.collections.LinkedHashMap


@Singleton
class UnitRepository @Inject constructor(
        @Remote val remoteDataSource: UnitDataSource.RemoteDataSource,
        @Local val localDataSource: UnitDataSource.LocalDataSource
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


    override fun findFromServer(): Flowable<FindResponse<UnitMeasure>> {
        return Flowable.just(null)
    }

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

    override fun findLastId(): Flowable<UnitMeasure> {
        return remoteDataSource.getLastRemote()
    }

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
                .getByIdRemote(id)
                .doOnNext { item ->
                    localDataSource.saveLocal(item)
                    cachedItem.put(item.id, item)
                }

        return Flowable.concat(local, remote)
                .firstElement()
                .toFlowable()
    }

    override fun save(model: UnitMeasure): Completable {
        return Completable.fromAction { remoteDataSource.saveRemote(UnitRequest(model.name, model.no)) }
                .andThen(localDataSource.saveLocal(model))
    }

    override fun save(model: UnitRequest): Flowable<UnitMeasure> {
        return remoteDataSource.saveRemote(UnitRequest(model.name, model.no))
                .flatMap { response ->
                    Flowable.just(response.model)
                            .doOnNext { source ->
                                Timber.i("task result $source")
                                localDataSource.saveLocal(source)
                                cachedItem.put(source.id, source)
                            }
                }
                .doOnComplete { cacheIsDirty = false }
    }

    override fun saveAll(models: List<UnitMeasure>): Completable {
        var postServer = arrayListOf<UnitRequest>()
        models.forEach { postServer.add(UnitRequest(it.name, it.no)) }
        return remoteDataSource.saveAllRemote(postServer)
                .andThen(localDataSource.saveAllLocal(models))
    }

    override fun update(model: UnitMeasure): Completable {
        return remoteDataSource.updateRemote(model)
                .andThen(localDataSource.updateLocal(model))
    }

    override fun refreshData() {
        cacheIsDirty = true
    }

    override fun deleteAll(): Completable {
        return remoteDataSource.deleteAllRemote()
                .andThen(localDataSource.deleteAllLocal())
    }

    override fun delete(id: Int): Completable {
        return remoteDataSource.deleteRemote(id)
                .andThen(localDataSource.deleteLocal(id))
    }


    private fun getAndCacheLocalTasks(): Flowable<List<UnitMeasure>> {
        return localDataSource
                .getAllLocal()
                .flatMap { models ->
                    Timber.i("tasks getAndCacheLocalTasks ${models.size}")
                    Flowable.fromIterable(models)
                            .doOnNext { task -> cachedItem.put(task.id, task) }
                            .toList()
                            .toFlowable()
                }
    }

    private fun getAndSaveRemoteTasks(): Flowable<List<UnitMeasure>> {
        return remoteDataSource
                .getAllRemote()
                .flatMap { response ->
                    Flowable
                            .fromIterable(response.content)
                            .doOnNext { source ->
                                Timber.i("task result $source")
                                localDataSource.saveLocal(source)
                                cachedItem.put(source.id, source)
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
                .getByIdLocal(id)
                .doOnNext { source ->
                    cachedItem.put(id, source)
                }
    }


}