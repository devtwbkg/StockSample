package xyz.twbkg.stock.data

import xyz.twbkg.stock.data.model.db.Category

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error<out T>(val throwable: Throwable, val data: T?) : Resource<T>()
    data class Failure<out T>(val throwable: Throwable) : Resource<T>()
}

//sealed class Resource<out T> {
//    object Loading : Resource<Nothing>()
//    data class Success<out T>(val data: T?) : Resource<T>()
//    data class Failure<out T>(val throwable: Throwable) : Resource<T>()
//}