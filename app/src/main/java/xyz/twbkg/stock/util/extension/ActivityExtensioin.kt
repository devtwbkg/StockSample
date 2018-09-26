package xyz.twbkg.stock.util.extension

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import xyz.twbkg.stock.R

fun AppCompatActivity.inflateFragment(fragment: Fragment) {
    this.supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
}