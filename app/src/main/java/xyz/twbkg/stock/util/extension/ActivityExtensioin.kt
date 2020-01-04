package xyz.twbkg.stock.util.extension

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import xyz.twbkg.stock.R

fun AppCompatActivity.inflateFragment(fragment: androidx.fragment.app.Fragment) {
    this.supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
}