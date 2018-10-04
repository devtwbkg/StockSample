package xyz.twbkg.stock.ui.unit

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.unit_product_fr.*
import timber.log.Timber
import xyz.twbkg.stock.ui.unit.adapter.UnitListAdapter
import xyz.twbkg.stock.ui.unit.dialog.CreateDialogFragment
import xyz.twbkg.stock.ui.unit.dialog.UpdateDialogFragment
import xyz.twbkg.stock.ui.unit.dialog.ViewDialogFragment
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.AdapterListAdapterCallback
import xyz.twbkg.stock.application.BaseFragment
import xyz.twbkg.stock.data.model.db.UnitMeasure
import javax.inject.Inject

class UnitFragment : BaseFragment(),
        UnitContract.View,
        UpdateDialogFragment.UpdateDialogListener,
        ViewDialogFragment.ViewDialogListener,
        CreateDialogFragment.CreateDialogListener {
    override fun showLoading() {
        progressbar?.visibility = View.VISIBLE
        swipeRefreshLayout?.isRefreshing = true
    }

    override fun hideLoading() {
        progressbar?.visibility = View.GONE
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun showResult(items: List<UnitMeasure>) {
        unitListAdapter.addItems(items)
    }

    override fun showResult(item: UnitMeasure) {
        unitListAdapter.addItem(item)
    }

    override fun updateReult(position: Int, item: UnitMeasure) {
        unitListAdapter.updateItem(position, item)
    }

    override fun showEmpty() {
        unitListAdapter.emptyItem()
    }

    override fun showSuccessMessage(messageString: Int) {
        Toast.makeText(context, messageString, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(messageString: Int) {
        Toast.makeText(context, messageString, Toast.LENGTH_SHORT).show()
    }

    private var errorSnackBar: Snackbar? = null
    private lateinit var unitListAdapter: UnitListAdapter

    @Inject
    lateinit var presenter: UnitContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.unit_product_fr, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        unitListAdapter = UnitListAdapter()
        unitListAdapter.addCallback(object : AdapterListAdapterCallback {
            override fun onItemSelected(position: Int) {
                Timber.d("item selected $position")
                presenter.findItem(position)
            }
        })
        unit_list?.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            adapter = unitListAdapter
        }
        swipeRefreshLayout?.apply {
            setOnRefreshListener {
                presenter.refreshData()
                presenter.findAll()
            }
        }
        if (savedInstanceState == null) {
            presenter.findAll()
        }
    }


    private fun showError(@StringRes errorMessage: Int) {
        showError(getString(errorMessage))
    }

    private fun showError(errorMessage: String = "") {
        if (errorMessage.isNotEmpty()) {
            errorSnackBar = Snackbar.make(roo_view, errorMessage, Snackbar.LENGTH_INDEFINITE)
            errorSnackBar?.setAction(R.string.retry) {}
            errorSnackBar?.show()
        }
    }

    private fun hideError() {
        progressbar?.visibility = View.GONE
        errorSnackBar?.dismiss()
    }

    private fun showEditDialog() {
        val dialogFragment = CreateDialogFragment.newInstance()
        dialogFragment.listener = this
        dialogFragment.show(fragmentManager, CreateDialogFragment::class.java.simpleName)
    }

    override fun showEditDialog(item: UnitMeasure) {
        val dialogFragment = UpdateDialogFragment.newInstance(item.name, item.description)
        dialogFragment.listener = this
        dialogFragment.show(fragmentManager, UpdateDialogFragment::class.java.simpleName)
    }

    private fun showViewDialog(unit: UnitMeasure) {
        val dialogFragment = ViewDialogFragment.newInstance(unit)
        dialogFragment.listener = this
        dialogFragment.show(fragmentManager, ViewDialogFragment::class.java.simpleName)
    }

    override fun onFinishEditDialog(name: String, description: String) {
        Timber.d("onFinishEditDialog $name description $description")
        if (name.isNotEmpty() && name.isNotBlank()) {
            presenter.save(name, description)
        } else {
            showError(R.string.please_input_your_name)
        }
    }

    /** end dialog call back **/
    fun onShowProgress() {
        progressbar?.visibility = View.VISIBLE
    }

    fun onHideProgress() {
        progressbar?.visibility = View.GONE
    }

    fun onAddMultipleSuccess(items: List<UnitMeasure>) {
        unitListAdapter.addItems(items)
    }

//    override fun onEditItem(unitMeasure: UnitMeasure) {
//        showEditDialog(unitMeasure)
//    }
//
//    override fun onViewItem(unitMeasure: UnitMeasure) {
//        showViewDialog(unitMeasure)
//    }
//
//    override fun showToast(messageString: Int) {
//        Toast.makeText(context, messageString, Toast.LENGTH_SHORT).show()
//    }

    fun onError(throwable: Throwable) {
        Timber.e("onError $throwable")
        throwable.message?.let { message ->
            showError(message)
        }
    }

    override fun onViewDialog(unit: UnitMeasure) {
//         showViewDialog(unit)
    }

    fun onError(errorMessages: Int) {
        showError(errorMessages)
    }

    override fun onDestroy() {
//        presenter?.onDestroy()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(
            menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.unit_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.finish()
                true
            }
            R.id.new_menu_item -> {
                showEditDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                UnitFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
