package xyz.twbkg.stock.application

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerView<T> : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    abstract fun addCallback(callback: AdapterListAdapterCallback)

    abstract fun addItem(item: T)

    abstract fun addItems(items: List<T>)

    abstract fun updateItems(items: List<T>)

    abstract fun emptyItem()

}