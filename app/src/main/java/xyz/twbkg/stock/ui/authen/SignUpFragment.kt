package xyz.twbkg.stock.ui.authen


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.BaseFragment

class SignUpFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sign_up_fr, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance() =
                SignUpFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
