package xyz.twbkg.stock.application

import androidx.annotation.StringRes

interface BaseView<T> {
    fun showLoading()
    fun hideLoading()


    fun showErrorMessage(@StringRes message:Int)
}