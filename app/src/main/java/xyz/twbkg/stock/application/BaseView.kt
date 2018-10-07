package xyz.twbkg.stock.application

import android.support.annotation.StringRes

interface BaseView<T> {
    fun showLoading()
    fun hideLoading()


    fun showErrorMessage(@StringRes message:Int)
}