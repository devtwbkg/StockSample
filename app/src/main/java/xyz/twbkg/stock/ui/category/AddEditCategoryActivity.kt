package xyz.twbkg.stock.ui.category

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.toolbar.*
import timber.log.Timber
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.BaseActivity
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.ui.category.addedit.AddEditCategoryFragment
import xyz.twbkg.stock.util.extension.configToolbars
import xyz.twbkg.stock.util.extension.inflateFragment

class AddEditCategoryActivity : BaseActivity() {

    lateinit var item: Category

    override fun enableHome(): Boolean = true
    override fun configToolbar(): Toolbar? = toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_with_toolbar)

        configToolbar()?.let { toolbar ->
            this.configToolbars(toolbar, enableHome())
        }

        item = intent.getParcelableExtra("item")
        Timber.d("item category $item")
        if (savedInstanceState == null) {
            inflateFragment(AddEditCategoryFragment.newInstance(item))
        }
    }

}
