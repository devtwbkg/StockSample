package xyz.twbkg.stock.application

import android.os.Bundle

interface BasePresenter<T> {

    /**
     * Drops the reference to the view when destroyed
     */
    fun dropView()

    fun restoreSavedInstanceState(savedInstanceState: Bundle)

    fun restoreArgument(arguments: Bundle)

    fun refreshData()


}