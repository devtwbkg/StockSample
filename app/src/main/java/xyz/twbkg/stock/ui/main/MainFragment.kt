package xyz.twbkg.stock.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_fragment.*
import timber.log.Timber
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.BaseFragment
import xyz.twbkg.stock.data.source.repository.CategoryRepo
import xyz.twbkg.stock.networking.Scheduler
import javax.inject.Inject

class MainFragment : BaseFragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var appScheduler: Scheduler


    private var errorSnackBar: Snackbar? = null

    private var disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}
