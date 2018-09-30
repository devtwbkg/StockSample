package xyz.twbkg.stock.application

import android.support.v7.widget.RecyclerView

abstract class BaseRecyclerView<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun addCallback(callback: AdapterListAdapterCallback)

    abstract fun addItem(item: T)

    abstract fun addItems(items: List<T>)

    abstract fun updateItems(items: List<T>)

    abstract fun emptyItem()

}