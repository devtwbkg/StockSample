package xyz.twbkg.stock.ui.category.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.view.*
import android.widget.LinearLayout
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.category_fragment.*
import timber.log.Timber
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.AdapterListAdapterCallback
import xyz.twbkg.stock.application.BaseFragment
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.networking.Scheduler
import xyz.twbkg.stock.ui.category.AddEditCategoryActivity
import xyz.twbkg.stock.ui.category.adapter.CategoryListAdapter
import javax.inject.Inject

class CategoryFragment : BaseFragment(),
        CategoryContract.View {


    companion object {
        fun newInstance() = CategoryFragment()
    }

    @Inject
    lateinit var appScheduler: Scheduler

    @Inject
    lateinit var presenter: CategoryPresenter

    lateinit var categoryListAdapter: CategoryListAdapter

    private var errorSnackBar: Snackbar? = null

    private var disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.category_fragment, container, false)
    }


    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        categoryListAdapter = CategoryListAdapter()

        categoryListAdapter.addCallback(object : AdapterListAdapterCallback {
            override fun onItemSelected(position: Int) {
                Timber.d("item selected $position")
                presenter.findAll()
            }
        })
        category_list?.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            adapter = categoryListAdapter
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

    override fun showResult(items: List<Category>) {
        categoryListAdapter.addItems(items)
    }

    override fun showEmpty() {
        categoryListAdapter.emptyItem()
    }

    private fun navigationToAddEdit() {
        startActivity(Intent(context, AddEditCategoryActivity::class.java))
    }

    override fun onCreateOptionsMenu(
            menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.unit_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_menu_item -> {
                navigationToAddEdit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
