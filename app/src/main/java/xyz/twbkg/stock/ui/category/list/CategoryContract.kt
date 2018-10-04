package xyz.twbkg.stock.ui.category.list

import android.support.annotation.StringRes
import xyz.twbkg.stock.application.BasePresenter
import xyz.twbkg.stock.application.BaseView
import xyz.twbkg.stock.data.model.db.Category

interface CategoryContract {

    interface View : BaseView<Presenter> {

        fun showLoading()

        fun hideLoading()

        fun showResult(items: List<Category>)

        fun showEmpty()

        fun navigationToAddEditCategory(item:Category)

        fun showErrorMessage(@StringRes message:Int)
    }

    interface Presenter : BasePresenter<View> {

        fun findAll()

        fun findItem(index: Int)
    }
}