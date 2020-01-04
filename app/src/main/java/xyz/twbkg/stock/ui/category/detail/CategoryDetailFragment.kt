package xyz.twbkg.stock.ui.category.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.BaseFragment

class CategoryDetailFragment : BaseFragment() {

    companion object {
        fun newInstance() = CategoryDetailFragment()
    }

    private lateinit var viewModel: CategoryDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.category_detail_ragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CategoryDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
