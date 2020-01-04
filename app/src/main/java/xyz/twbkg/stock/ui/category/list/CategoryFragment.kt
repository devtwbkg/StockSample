package xyz.twbkg.stock.ui.category.list

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
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
                presenter.findItem(position)
            }
        })
        category_list?.apply {
            addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(context, LinearLayout.VERTICAL))
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

    override fun navigationToAddEditCategory(item: Category) {
        Intent(context, AddEditCategoryActivity::class.java).apply {
            putExtra("item", item)
        }.also { startActivity(it) }
    }

    override fun showErrorMessage(message: Int) {
        Toast.makeText(context,
                message,
                Toast.LENGTH_SHORT)
                .show()
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
