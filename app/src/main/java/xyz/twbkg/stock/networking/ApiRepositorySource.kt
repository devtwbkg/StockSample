//package xyz.twbkg.stock.networking
//
//import android.support.annotation.MainThread
//import android.support.annotation.WorkerThread
//
//import io.reactivex.BackpressureStrategy
//import io.reactivex.Flowable
//import io.reactivex.Observable
//import io.reactivex.functions.Consumer
//import io.reactivex.schedulers.Schedulers
//import retrofit2.HttpException
//import xyz.twbkg.stock.networking.ApiResource.Companion.loading
//import xyz.twbkg.stock.networking.ApiResource.Companion.success
//
//abstract class ApiRepositorySource<RawResponse : BaseResponse, ResultType> @MainThread
//internal constructor(private val appDatabase: AppDatabase) {
//
//    // result is a Flowable because Room Database only returns Flowables
//    // Retrofit response will also be folded into the stream as a Flowable
//    private val result: Flowable<ApiResource<ResultType>>
//
//    init {
//        val source: Flowable<ApiResource<ResultType>>
//        if (shouldFetch()) {
//            source = createCall()
//                    .doOnNext { this.saveCallResult(it) }
//                    .flatMap<ApiResource<ResultType>> { apiResponse ->
//                        loadFromDb().toObservable().map<ApiResource<ResultType>> { data -> ApiResource.success(data) }
//                    }
//                    .doOnError { this.onFetchFailed(it) }
//                    .onErrorResumeNext { t ->
//                        loadFromDb()
//                                .toObservable()
//                                .map<ApiResource> { data ->
//                                    val apiResource: ApiResource<*>
//
//                                    apiResource = if (t is HttpException && (t as HttpException).code() >= 400 && (t as HttpException).code() < 500) {
//                                        ApiResource.invalid(t.message, data)
//                                    } else {
//                                        ApiResource.error(t.message, data)
//                                    }
//
//                                    apiResource
//                                }
//                    }
//                    .toFlowable(BackpressureStrategy.LATEST)
//        } else {
//            source = loadFromDb()
//                    .subscribeOn(Schedulers.io())
//                    .map(Function<ResultType, ApiResource<ResultType>> { success() })
//        }
//
//        result = Flowable.concat<Any>(initLoadDb()
//                .map(Function<ResultType, Any> { loading() })
//                .take(1),
//                source)
//                .subscribeOn(Schedulers.io())
//    }
//
//    fun asObservable(): Observable<ApiResource<ResultType>> {
//        return result.toObservable()
//    }
//
//    protected fun onFetchFailed(t: Throwable) {
//        t.printStackTrace()
//    }
//
//    @WorkerThread
//    protected fun saveCallResult(resultType: RawResult) {
//        resultType.saveResponseToDb(appDatabase)
//    }
//
//    @MainThread
//    protected abstract fun shouldFetch(): Boolean
//
//    @MainThread
//    protected abstract fun loadFromDb(): Flowable<ResultType>
//
//    @MainThread
//    protected abstract fun createCall(): Observable<RawResult>
//
//    @MainThread
//    protected fun initLoadDb(): Flowable<ResultType> {
//        return loadFromDb()
//    }
//}