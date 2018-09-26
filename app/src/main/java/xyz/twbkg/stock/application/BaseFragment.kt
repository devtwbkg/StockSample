package xyz.twbkg.stock.application

import android.os.Bundle
import android.support.v4.app.Fragment
import xyz.twbkg.stock.di.common.Injectable

abstract class BaseFragment : Fragment(), Injectable {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}