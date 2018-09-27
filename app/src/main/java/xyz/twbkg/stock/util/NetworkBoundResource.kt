package xyz.twbkg.stock.util

import android.content.Context
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.exceptions.Exceptions
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import xyz.twbkg.stock.data.Resource
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.experimental.coroutineContext

abstract class NetworkBoundResource<ResultType, RequestType>(var networkUtils: NetworkUtils) {
//
//    @Inject
//    lateinit var networkUtil: NetworkUtils
//    @Inject
//    lateinit var context: Context
    private val result: Flowable<Resource<ResultType>>

    init {
        // Lazy disk observable.
        val diskObservable = Flowable.defer {
            loadFromDb()
                    // Read from disk on Computation Scheduler
                    .subscribeOn(Schedulers.computation())
        }

        // Lazy network observable.
        val networkObservable = Flowable.defer {
            createCall()
                    // Request API on IO Scheduler
                    .subscribeOn(Schedulers.io())
                    // Read/Write to disk on Computation Scheduler
                    .observeOn(Schedulers.computation())
                    .doOnNext { request ->
                        saveCallResult(request)
                    }
                    .onErrorReturn { throwable: Throwable ->
                        when (throwable) {
                            is HttpException -> {
                                throw Exceptions.propagate(throwable)
//                                throw Exceptions.propagate(NetworkExceptions.getNoServerConnectivityError(context))
                            }
                            is IOException -> {
                                throw Exceptions.propagate(throwable)
//                                throw Exceptions.propagate(NetworkExceptions.getNoNetworkConnectivityError(context))
                            }
                            else -> {
                                throw Exceptions.propagate(throwable)
//                                throw Exceptions.propagate(NetworkExceptions.getUnexpectedError(context))
                            }
                        }
                    }
                    .flatMap { loadFromDb() }
        }

        result = when {
            networkUtils.hasNetworkConnection() -> networkObservable
                    .map<Resource<ResultType>> { Resource.Success(it) }
                    .onErrorReturn { Resource.Failure(it) }
                    // Read results in Android Main Thread (UI)
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(Resource.Loading)
            else -> diskObservable
                    .map<Resource<ResultType>> { Resource.Success(it) }
                    .onErrorReturn { Resource.Failure(it) }
                    // Read results in Android Main Thread (UI)
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(Resource.Loading)
        }
    }

    fun asFlowable(): Flowable<Resource<ResultType>> {
        return result
    }

    protected abstract fun saveCallResult(request: RequestType)

    protected abstract fun loadFromDb(): Flowable<ResultType>

    protected abstract fun createCall(): Flowable<RequestType>
}