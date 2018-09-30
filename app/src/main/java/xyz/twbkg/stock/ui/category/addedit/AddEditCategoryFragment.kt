package xyz.twbkg.stock.ui.category.addedit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.Toast
import kotlinx.android.synthetic.main.add_edit_category_fragment.*
import timber.log.Timber
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.BaseFragment
import javax.inject.Inject

class AddEditCategoryFragment : BaseFragment(),
        AddEditCategoryContract.View {
    @Inject
    lateinit var presenter: AddEditCategoryPresenter

    override fun showEmptyError() {
        Toast.makeText(context, R.string.please_input_your_name, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessMessage(messageString: Int) {
        Toast.makeText(context, messageString, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(messageString: Int) {
        Toast.makeText(context, messageString, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.add_edit_category_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addListener()
        updateTitle("${getString(R.string.title_add)} ${getString(R.string.title_category)}")
    }

    private fun validateField(): Boolean {
        val name = input_name_edt?.text.toString()
        val description = input_description_edt?.text.toString()
        Timber.d("validateField $name description $description")
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
        input_name_edt?.text?.clear()
        input_description_edt?.text?.clear()
    }

    private fun createCategory() {
        if (validateField()) {
            presenter?.save(
                    input_name_edt?.text.toString(),
                    input_description_edt?.text.toString()
            )
        }
    }

    companion object {
        fun newInstance() = AddEditCategoryFragment()
    }

}
