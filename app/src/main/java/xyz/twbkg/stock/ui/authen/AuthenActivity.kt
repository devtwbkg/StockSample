package xyz.twbkg.stock.ui.authen

import android.os.Bundle
import android.support.v7.widget.Toolbar
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.BaseActivity
import xyz.twbkg.stock.ui.authen.signin.LoginFragment
import xyz.twbkg.stock.util.extension.inflateFragment

class AuthenActivity : BaseActivity() {
    override fun enableHome(): Boolean = false

    override fun configToolbar(): Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authen_ac)

        inflateFragment(LoginFragment.newInstance())
    }

    private fun namageLayout(){

    }
}
