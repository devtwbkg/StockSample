package xyz.twbkg.stock.util.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


fun AppCompatActivity.configToolbars(toolbar: Toolbar, isEnableHomeButton: Boolean = false) {
    this.apply {
        setSupportActionBar(toolbar)
        supportActionBar?.let { actionbar ->
            actionbar.setDisplayShowHomeEnabled(isEnableHomeButton)
            actionbar.setDisplayHomeAsUpEnabled(isEnableHomeButton)
        }
    }
}


fun AppCompatActivity.title(title: String) {
    this.apply {
        supportActionBar?.let { actionbar ->
            actionbar.title =title
        }
    }
}
