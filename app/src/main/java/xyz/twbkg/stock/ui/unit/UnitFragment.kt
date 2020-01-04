package xyz.twbkg.stock.ui.unit

import android.content.Context
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.DividerItemDecoration
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.unit_product_fr.*
import timber.log.Timber
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.AdapterListAdapterCallback
import xyz.twbkg.stock.application.BaseFragment
import xyz.twbkg.stock.common.ErrorDialogFragment
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.extensions.handle
import xyz.twbkg.stock.extensions.handleException
import xyz.twbkg.stock.ui.common.Resolution
import xyz.twbkg.stock.ui.common.ResolutionByCase
import xyz.twbkg.stock.ui.common.UIResolution
import xyz.twbkg.stock.ui.common.UIResolver
import xyz.twbkg.stock.ui.unit.adapter.UnitListAdapter
import xyz.twbkg.stock.ui.unit.dialog.CreateDialogFragment
import xyz.twbkg.stock.ui.unit.dialog.UpdateDialogFragment
import xyz.twbkg.stock.ui.unit.dialog.ViewDialogFragment
import javax.inject.Inject

class UnitFragment : BaseUnitFragment(),
        UnitContract.View,
        UpdateDialogFragment.UpdateDialogListener,
        ViewDialogFragment.ViewDialogListener,
        CreateDialogFragment.CreateDialogListener {

    private var errorSnackBar: Snackbar? = null
    private lateinit var unitListAdapter: UnitListAdapter

    @Inject
    lateinit var presenter: UnitContract.Presenter

    @Inject
    lateinit var uiResolver: UIResolver

    @Inject
    lateinit var uiResolution: UIResolution

    override fun rootView(): View = root_view

    override fun contextView(): Context {
        return context!!
    }

    override fun getResolution(): Resolution {
        return uiResolution
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.unit_product_fr, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        uiResolver.provideRootView(rootView(), fragmentManager)

        unitListAdapter = UnitListAdapter()
        unitListAdapter.addCallback(object : AdapterListAdapterCallback {
            override fun onItemSelected(position: Int) {
                Timber.d("item selected $position")
                presenter.findItem(position)
            }
        })
        unit_list?.apply {
            addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(context, LinearLayout.VERTICAL))
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

    override fun updateResult(position: Int, item: UnitMeasure) {
        unitListAdapter.updateItem(position, item)
    }

    override fun showEmpty() {
        unitListAdapter.emptyItem()
    }

    override fun showSuccessMessage(messageString: Int) {
        Toast.makeText(context, messageString, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(message: Int) {
        showSnackBar(getString(message))
    }


    override fun showLoading(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showDialogErrorMessage(message: Int) {
        val dialog = ErrorDialogFragment.newInstance(getString(message))
        if (!dialog.isVisible) {
            dialog.show(fragmentManager, ErrorDialogFragment::class.java.simpleName)
        }
    }

    override fun showSnackBarErrorMessage(message: Int) {
        showSnackBar(getString(message))
    }

    override fun showSnackBarErrorMessage(message: String) {
        showSnackBar(message)
    }

    override fun showToastErrorMessage(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showToastErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun handleError(throwable: Throwable) {
    }

    private fun showSnackBar(message: String) {
        val errorSnackBar = Snackbar.make(
                root_view,
                message,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) {
                    presenter.findAll()
                }
        errorSnackBar?.show()
    }

    private fun showEditDialog() {
        val dialogFragment = CreateDialogFragment.newInstance()
        dialogFragment.listener = this
        dialogFragment.show(fragmentManager, CreateDialogFragment::class.java.simpleName)
    }

    override fun showEditDialog(item: UnitMeasure) {
        val dialogFragment = UpdateDialogFragment.newInstance(item.name, item.no)
        dialogFragment.listener = this
        dialogFragment.show(fragmentManager, UpdateDialogFragment::class.java.simpleName)
    }

    private fun showViewDialog(unit: UnitMeasure) {
        val dialogFragment = ViewDialogFragment.newInstance(unit)
        dialogFragment.listener = this
        dialogFragment.show(fragmentManager, ViewDialogFragment::class.java.simpleName)
    }

    override fun onFinishEditDialog(name: String, description: String) {
        Timber.d("onFinishEditDialog $name no $description")
        if (name.isNotEmpty() && name.isNotBlank()) {
            presenter.save(name, description)
        } else {
            showErrorMessage(R.string.please_input_your_name)
        }
    }


    override fun onViewDialog(unit: UnitMeasure) {
//         showViewDialog(unit)
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
