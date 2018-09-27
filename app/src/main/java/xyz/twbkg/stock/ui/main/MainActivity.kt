package xyz.twbkg.stock.ui.main

import android.os.Bundle
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.toolbar.*
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.BaseActivity
import xyz.twbkg.stock.util.extension.configToolbars
import xyz.twbkg.stock.util.extension.inflateFragment

class MainActivity : BaseActivity() {
    override fun enableHome(): Boolean = true
    override fun configToolbar(): Toolbar? = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_with_toolbar)

        configToolbar()?.let { toolbar ->
            this.configToolbars(toolbar, enableHome())
        }
        if (savedInstanceState == null) {
            inflateFragment(MainFragment.newInstance())
        }
    }

}
