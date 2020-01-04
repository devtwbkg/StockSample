package xyz.twbkg.stock.networking

import android.annotation.SuppressLint
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
internal constructor() {

    private val result: Observable<ApiResource<ResultType>>
    private lateinit var source: Observable<ApiResource<ResultType>>

    init {
        // Request API on IO Scheduler
         loadFromDb().subscribeOn(Schedulers.io())
                // Read/Write to disk on Computation Scheduler
                .observeOn(Schedulers.computation())
                .doOnNext { request ->
                    source = if (shouldFetch(request)) {
                        fetFromNetwork()
                    } else {
                        loadFromDb()
                                .toObservable()
                                .map<ApiResource<ResultType>> { ApiResource.success(it) }
                    }

                }
                .subscribe()

//        source = if (shouldFetch(loadFromDb().blockingFirst())) {
//            fetFromNetwork()
//        } else {
//            loadFromDb()
//                    .toObservable()
//                    .map<ApiResource<ResultType>> { ApiResource.success(it) }
//        }


//        loadFromDb()
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    source = if (shouldFetch(it)) {
//                        fetFromNetwork()
//                    } else {
//                        loadFromDb()
//                                .toObservable()
//                                .map<ApiResource<ResultType>> { ApiResource.success(it) }
//                    }
//
//                }, { error -> Timber.e("Load data from local error $error") }, {
//
//                })
        result = Observable.concat<ApiResource<ResultType>>(
                loadFromDb()
                        .toObservable()
                        .map<ApiResource<ResultType>> { ApiResource.loading(null) }
                        .take(1),
                source
        )

//        if (shouldFetch()) {
//            source = createCall()
//                    .subscribeOn(Schedulers.io())
//                    .doOnNext { apiResponse -> saveCallResult(apiResponse) }
//                    .flatMap<ApiResource<ResultType>> { apiResponse -> loadFromDb().toObservable().map<ApiResource<ResultType>> { data -> ApiResource.success(data) } }
//                    .doOnError { t -> onFetchFailed() }
//                    .onErrorResumeNext { t: Throwable ->
//                        loadFromDb()
//                                .toObservable()
//                                .map<ApiResource<ResultType>> { data -> ApiResource.error(t.message!!, data) }
//
//                    }
//                    .observeOn(AndroidSchedulers.mainThread())
//        } else {
//            source = loadFromDb()
//                    .toObservable()
//                    .map<ApiResource<ResultType>> { ApiResource.success(it) }
//        }
//
//        result = Observable.concat<ApiResource<ResultType>>(
//                loadFromDb()
//                        .toObservable()
//                        .map<ApiResource<ResultType>> { ApiResource.loading(null) }
//                        .take(1),
//                source
//        )
    }

    fun fetFromNetwork() = createCall()
            .subscribeOn(Schedulers.io())
            .doOnNext { apiResponse -> saveCallResult(apiResponse) }
            .flatMap<ApiResource<ResultType>> { _ -> loadFromDb().toObservable().map<ApiResource<ResultType>> { data -> ApiResource.success(data) } }
            .doOnError { t -> onFetchFailed() }
            .onErrorResumeNext { t: Throwable ->
                loadFromDb()
                        .toObservable()
                        .map<ApiResource<ResultType>> { data -> ApiResource.error(t.message!!, data) }

            }
            .observeOn(AndroidSchedulers.mainThread())

    fun asObservable(): Observable<ApiResource<ResultType>> {


        return result
    }

    protected fun onFetchFailed() {}

//    @WorkerThread
//    protected fun processResponse(response: ApiResponse<RequestType>): RequestType {
//        return response.body
//    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType): Boolean

    @MainThread
    protected abstract fun loadFromDb(): Flowable<ResultType>

    @MainThread
    protected abstract fun createCall(): Observable<RequestType>
}