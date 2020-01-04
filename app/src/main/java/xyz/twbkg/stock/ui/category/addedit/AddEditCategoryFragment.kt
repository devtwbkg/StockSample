package xyz.twbkg.stock.ui.category.addedit

import android.os.Bundle
import androidx.annotation.StringRes
import android.view.*
import android.widget.Toast
import kotlinx.android.synthetic.main.add_edit_category_fragment.*
import timber.log.Timber
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.BaseFragment
import xyz.twbkg.stock.data.model.db.Category
import javax.inject.Inject

class AddEditCategoryFragment : BaseFragment(),
        AddEditCategoryContract.View {
    @Inject
    lateinit var presenter: AddEditCategoryPresenter

    private var category: Category? = null

    override fun showEmptyError() {
        Toast.makeText(context, R.string.please_input_your_name, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessMessage(messageString: Int) {
        Toast.makeText(context, messageString, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            arguments?.let { restoreArgument(it) }
        } else {
            if (savedInstanceState != null) {
                restoreSavedInstanceState(savedInstanceState)
            }
        }
    }

    private fun restoreSavedInstanceState(savedInstanceState: Bundle) {
        category = savedInstanceState.getParcelable(ARG_CATEGORY)
    }

    private fun restoreArgument(arguments: Bundle) {
        arguments?.let {
            category = it.getParcelable("category")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.add_edit_category_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addListener()
        updateTitle("${getString(R.string.title_add)} ${getString(R.string.title_category)}")

        category?.let {
            setIdToPresenter(it.id)
            setModelToView(it.name, it.description)
        }
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setIdToPresenter(id: Int) {
        presenter.setId(id)
    }

    private fun setModelToView(name: String, description: String) {
        input_username_or_email_edt?.apply { setText(name) }
        input_password_edt?.apply { setText(description) }
    }

    private fun validateField(): Boolean {
        val name = input_username_or_email_edt?.text.toString()
        val description = input_password_edt?.text.toString()
        Timber.d("validateField $name no $description")
        if (name.isEmpty() || description.isEmpty()) {
            showEmptyError()
            return false
        }
        return true
    }

    private fun addListener() {

        submit_btn?.apply {
            setOnClickListener {
                createCategory()
            }
        }
    }

    override fun clearInputBox() {
        input_username_or_email_edt?.text?.clear()
        input_password_edt?.text?.clear()
    }

    private fun createCategory() {
        if (validateField()) {
            presenter?.save(
                    input_username_or_email_edt?.text.toString(),
                    input_password_edt?.text.toString()
            )
        }
    }

    companion object {
        const val ARG_CATEGORY = "catgory"
        fun newInstance() = AddEditCategoryFragment()
        fun newInstance(category: Category): AddEditCategoryFragment {
            return AddEditCategoryFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CATEGORY, category)
                }
            }
        }
    }
}
