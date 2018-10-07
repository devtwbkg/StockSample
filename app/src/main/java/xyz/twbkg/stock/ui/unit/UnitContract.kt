package xyz.twbkg.stock.ui.unit

import android.content.Context
import android.support.annotation.StringRes
import xyz.twbkg.stock.application.BasePresenter
import xyz.twbkg.stock.application.BaseView
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.ui.common.Resolution

interface UnitContract {

    interface View : BaseView<Presenter> {

        fun contextView(): Context
        fun showResult(items: List<UnitMeasure>)
        fun showResult(item: UnitMeasure)
        fun updateResult(position: Int, item: UnitMeasure)
        fun showEmpty()
        fun showSuccessMessage(@StringRes messageString: Int)
        fun showEditDialog(item: UnitMeasure)

        fun getResolution(): Resolution

        fun showLoading(message: String)
        fun showDialogErrorMessage(@StringRes message: Int)
        fun showSnackBarErrorMessage(@StringRes message: Int)
        fun showSnackBarErrorMessage(@StringRes message: String)
        fun showToastErrorMessage(@StringRes message: Int)
        fun showToastErrorMessage(@StringRes message: String)
        fun handleError(throwable: Throwable)
    }

    interface Presenter : BasePresenter<View> {

        fun findAll()
        fun findItem(index: Int)
        fun save(name: String, description: String)
    }
}