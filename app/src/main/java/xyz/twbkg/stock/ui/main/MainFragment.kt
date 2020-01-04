package xyz.twbkg.stock.ui.main

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.main_fragment.*
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.BaseFragment
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
