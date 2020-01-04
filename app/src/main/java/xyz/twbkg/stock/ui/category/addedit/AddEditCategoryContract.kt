package xyz.twbkg.stock.ui.category.addedit

import androidx.annotation.StringRes
import xyz.twbkg.stock.application.BasePresenter
import xyz.twbkg.stock.application.BaseView

interface AddEditCategoryContract {

    interface View : BaseView<Presenter> {

        fun clearInputBox()

        fun showEmptyError()

        fun showSuccessMessage(@StringRes messageString: Int)
    }

    interface Presenter : BasePresenter<View> {

        fun save(title: String, description: String)

    }
}