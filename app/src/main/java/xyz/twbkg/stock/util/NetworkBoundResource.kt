package xyz.twbkg.stock.util

import android.arch.lifecycle.Transformations.map
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.exceptions.Exceptions
import io.reactivex.schedulers.Schedulers
import org.intellij.lang.annotations.Flow
import retrofit2.HttpException
import timber.log.Timber
import xyz.twbkg.stock.data.Resource
import xyz.twbkg.stock.data.model.db.Category
import java.io.IOException

abstract class NetworkBoundResource<ResultType, RequestType>(networkUtils: NetworkUtils) {
    private lateinit var result: Flowable<Resource<ResultType>>
    lateinit var resultLocal: Resource<ResultType>
//    init {
//        // Lazy disk observable.
//        val diskObservable = Flowable.defer {
//            loadFromDb()
//                    // Read from disk on Computation Scheduler
//                    .subscribeOn(Schedulers.computation())
//        }
//
//        // Lazy network observable.
//        val networkObservable = Flowable.defer {
//            createCall()
//                    // Request API on IO Scheduler
//                    .subscribeOn(Schedulers.io())
//                    // Read/Write to disk on Computation Scheduler
//                    .observeOn(Schedulers.computation())
//                    .doOnNext { request -> saveCallResult(request) }
//                    .onErrorReturn { throwable: Throwable ->
//                        //                        Timber.e("onErrorReturn $throwable")
//                        when (throwable) {
//                            is HttpException -> {
//                                throw Exceptions.propagate(throwable)
//                                // throw Exceptions.propagate(NetworkExceptions.getNoServerConnectivityError(context))
//                            }
//                            is IOException -> {
//                                throw Exceptions.propagate(throwable)
//                                // throw Exceptions.propagate(NetworkExceptions.getNoNetworkConnectivityError(context))
//                            }
//                            else -> {
//                                throw Exceptions.propagate(throwable)
//                                // throw Exceptions.propagate(NetworkExceptions.getUnexpectedError(context))
//                            }
//                        }
//                    }
//                    .flatMap { loadFromDb() }
//        }
//
////        result = when {
////            networkUtils.hasNetworkConnection() -> networkObservable
////                    .map<Resource<ResultType>> { Resource.Success(it) }
////                    .onErrorReturn {
////                        loadFromDb().blockingFirst()
////                        if (loadFromDb().blockingFirst() != null) {
////                            Resource.Error(it, loadFromDb().blockingFirst())
////                        } else {
////                            Resource.Failure(it)
////                        }
////                    }
////                    // Read results in Android Main Thread (UI)
////                    .observeOn(AndroidSchedulers.mainThread())
////                    .startWith(Resource.Loading)
////            else -> diskObservable
////                    .map<Resource<ResultType>> { Resource.Success(it) }
////                    .onErrorReturn { Resource.Failure(it) }
////                    // Read results in Android Main Thread (UI)
////                    .observeOn(AndroidSchedulers.mainThread())
////                    .startWith(Resource.Loading)
////        }
//
//
//    }


    init {
        val networkObservable = Flowable.defer {
            createCall()
                    // Request API on IO Scheduler
                    .subscribeOn(Schedulers.io())
                    // Read/Write to disk on Computation Scheduler
                    .observeOn(Schedulers.computation())
                    .doOnNext { request -> saveCallResult(request) }
                    .onErrorReturn { throwable: Throwable ->
                        //                        Timber.e("onErrorReturn $throwable")
                        when (throwable) {
                            is HttpException -> {
                                throw Exceptions.propagate(throwable)
                                // throw Exceptions.propagate(NetworkExceptions.getNoServerConnectivityError(context))
                            }
                            is IOException -> {
                                throw Exceptions.propagate(throwable)
                                // throw Exceptions.propagate(NetworkExceptions.getNoNetworkConnectivityError(context))
                            }
                            else -> {
                                throw Exceptions.propagate(throwable)
                                // throw Exceptions.propagate(NetworkExceptions.getUnexpectedError(context))
                            }
                        }
                    }
                    .flatMap { loadFromDb() }
        }


        val loadLocalData = loadFromDb()
                // Request API on IO Scheduler
                .subscribeOn(Schedulers.io())
                // Read/Write to disk on Computation Scheduler
                .observeOn(Schedulers.computation())
                .doOnNext { request ->
                    resultLocal = Resource.Success(request)
                    Resource.LocalSuccess(request)
                }
                .doOnError { error -> Timber.e("laod from local error $error") }


        result = when {
            networkUtils.hasNetworkConnection() -> networkObservable
                    .map<Resource<ResultType>> { Resource.Success(it) }
                    .onErrorReturn {
                        if (::resultLocal.isInitialized) {
                            resultLocal
                        } else {
                            Resource.Failure(it)
                        }
                    }
                    // Read results in Android Main Thread (UI)
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(Resource.Loading)
            else -> loadLocalData
                    .map<Resource<ResultType>> { Resource.Success(it) }
                    .onErrorReturn { Resource.Failure(it) }
                    // Read results in Android Main Thread (UI)
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(Resource.Loading)
        }
    }

    fun asFlowable(): Flowable<Resource<ResultType>> = result

    protected abstract fun shouldFetch(data: ResultType): Boolean

    protected abstract fun saveCallResult(request: RequestType)

    protected abstract fun loadFromDb(): Flowable<ResultType>

    protected abstract fun createCall(): Flowable<RequestType>

    protected abstract fun createFail(): Flowable<RequestType>
}