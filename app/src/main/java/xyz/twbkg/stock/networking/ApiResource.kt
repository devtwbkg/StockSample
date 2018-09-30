package xyz.twbkg.stock.networking

import xyz.twbkg.stock.networking.Status.*

data class ApiResource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): ApiResource<T> {

            return ApiResource(SUCCESS, data, null)

        }


        fun <T> error(msg: String, data: T?): ApiResource<T> {

            return ApiResource(ERROR, data, msg)

        }


        fun <T> loading(data: T?): ApiResource<T> {

            return ApiResource(LOADING, data, null)

        }

    }

}