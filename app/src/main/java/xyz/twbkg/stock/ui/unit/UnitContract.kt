package xyz.twbkg.stock.ui.unit

import android.support.annotation.StringRes
import xyz.twbkg.stock.application.BasePresenter
import xyz.twbkg.stock.application.BaseView
import xyz.twbkg.stock.data.model.db.UnitMeasure

interface UnitContract {

    interface View : BaseView<Presenter> {

        fun showLoading()

        fun hideLoading()

        fun showResult(items: List<UnitMeasure>)

        fun showResult(item: UnitMeasure)

        fun updateReult(position: Int, item: UnitMeasure)

        fun showEmpty()

        fun showSuccessMessage(@StringRes messageString: Int)

        fun showErrorMessage(@StringRes messageString: Int)

        fun showEditDialog(item: UnitMeasure)

    }

    interface Presenter : BasePresenter<View> {

        fun findAll()

        fun findItem(index: Int)

        fun save(name: String, description: String)
    }
}