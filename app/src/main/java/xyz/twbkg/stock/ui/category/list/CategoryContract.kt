package xyz.twbkg.stock.ui.category.list

import xyz.twbkg.stock.application.BasePresenter
import xyz.twbkg.stock.application.BaseView
import xyz.twbkg.stock.data.model.db.Category

interface CategoryContract {

    interface View : BaseView<Presenter> {

        fun showLoading()

        fun hideLoading()

        fun showResult(items: List<Category>)

        fun showEmpty()
    }

    interface Presenter : BasePresenter<View> {

        fun findAll()

        fun refreshData()

    }
}