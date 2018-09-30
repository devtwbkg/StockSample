//package xyz.twbkg.stock.networking.synk
//
//import android.arch.lifecycle.LiveData
//import android.arch.lifecycle.MediatorLiveData
//import android.support.annotation.MainThread
//import android.support.annotation.WorkerThread
//import xyz.twbkg.stock.data.ApiResource
//import xyz.twbkg.stock.networking.ApiResource
//import xyz.twbkg.stock.util.ApiResponse
//
///**
// * A generic class that can provide a resource backed by both the sqlite database and the network.
// *
// *
// * You can read more about it in the [Architecture
// * Guide](https://developer.android.com/arch).
// * @param <ResultType>
// * @param <RequestType>
//</RequestType></ResultType> */
//abstract class NetworkBoundResource<ResultType, RequestType>
//@MainThread constructor() {
//
//    private val result = MediatorLiveData<ApiResource<ResultType>>()
//
//    init {
//        result.value = ApiResource.loading(null)
//        @Suppress("LeakingThis")
//        val dbSource = loadFromDb()
//        result.addSource(dbSource) { data ->
//            result.removeSource(dbSource)
//            if (shouldFetch(data)) {
//                fetchFromNetwork(dbSource)
//            } else {
//                result.addSource(dbSource) { newData ->
//                    setValue(ApiResource.success(newData))
//                }
//            }
//        }
//    }
//
//    @MainThread
//    private fun setValue(newValue: ApiResource<ResultType>) {
//        if (result.value != newValue) {
//            result.value = newValue
//        }
//    }
//
//    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
//        val apiResponse = createCall()
//        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
//        result.addSource(dbSource) { newData ->
//            setValue(ApiResource.loading(newData))
//        }
//        result.addSource(apiResponse) { response ->
//            result.removeSource(apiResponse)
//            result.removeSource(dbSource)
//            when (response) {
//                is ApiSuccessResponse -> {
//                    appExecutors.diskIO().execute {
//                        saveCallResult(processResponse(response))
//                        appExecutors.mainThread().execute {
//                            // we specially request a new live data,
//                            // otherwise we will get immediately last cached value,
//                            // which may not be updated with latest results received from network.
//                            result.addSource(loadFromDb()) { newData ->
//                                setValue(ApiResource.success(newData))
//                            }
//                        }
//                    }
//                }
//                is ApiEmptyResponse -> {
//                    appExecutors.mainThread().execute {
//                        // reload from disk whatever we had
//                        result.addSource(loadFromDb()) { newData ->
//                            setValue(ApiResource.success(newData))
//                        }
//                    }
//                }
//                is ApiErrorResponse -> {
//                    onFetchFailed()
//                    result.addSource(dbSource) { newData ->
//                        setValue(ApiResource.error(response.errorMessage, newData))
//                    }
//                }
//            }
//        }
//    }
//
//    protected open fun onFetchFailed() {}
//
//    fun asLiveData() = result as LiveData<ApiResource<ResultType>>
//
//    @WorkerThread
//    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body
//
//    @WorkerThread
//    protected abstract fun saveCallResult(item: RequestType)
//
//    @MainThread
//    protected abstract fun shouldFetch(data: ResultType?): Boolean
//
//    @MainThread
//    protected abstract fun loadFromDb(): LiveData<ResultType>
//
//    @MainThread
//    protected abstract fun createCall(): LiveData<RequestType>
//}