package xyz.twbkg.stock.ui.category.list

import androidx.annotation.StringRes
import xyz.twbkg.stock.application.BasePresenter
import xyz.twbkg.stock.application.BaseView
import xyz.twbkg.stock.data.model.db.Category

interface CategoryContract {

    interface View : BaseView<Presenter> {


        fun showResult(items: List<Category>)

        fun showEmpty()

        fun navigationToAddEditCategory(item:Category)

        override fun showErrorMessage(@StringRes message:Int)
    }

    interface Presenter : BasePresenter<View> {

        fun findAll()

        fun findItem(index: Int)
    }
}