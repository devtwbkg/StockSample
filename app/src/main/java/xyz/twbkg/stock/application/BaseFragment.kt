package xyz.twbkg.stock.application

import android.os.Bundle
import android.support.v4.app.Fragment
import xyz.twbkg.stock.di.common.Injectable
import android.support.v7.app.AppCompatActivity


abstract class BaseFragment : Fragment(), Injectable {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    fun updateTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }
}