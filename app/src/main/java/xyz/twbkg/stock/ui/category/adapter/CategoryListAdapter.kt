package xyz.twbkg.stock.ui.category.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_category.view.*
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.AdapterListAdapterCallback
import xyz.twbkg.stock.application.BaseRecyclerView
import xyz.twbkg.stock.application.EmptyViewHolder
import xyz.twbkg.stock.data.model.db.Category


class CategoryListAdapter : BaseRecyclerView<Category>() {


    private var itemList: ArrayList<Category> = arrayListOf()
    private lateinit var callback: AdapterListAdapterCallback

    private val TYPE_ITEM = R.layout.item_category
    private val TYPE_EMPTY = R.layout.item_empty


    override fun addCallback(callback: AdapterListAdapterCallback) {
        this.callback = callback
    }

    override fun addItem(item: Category) {
        this.itemList.add(item)
        notifyItemInserted(itemList.size - 1)
    }

    override fun addItems(items: List<Category>) {
        this.itemList.clear()
        this.itemList.addAll(items)
        val afterAdd = this.itemList.size
        notifyDataSetChanged()
    }

    override fun updateItems(items: List<Category>) {
        this.itemList.addAll(items)
        notifyDataSetChanged()
    }

    override fun emptyItem() {
        this.itemList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val binding: View = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return if (viewType == TYPE_ITEM) {
            ItemViewHolder(binding)
        } else {
            EmptyViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList.size == 0) {
            TYPE_EMPTY
        } else {
            TYPE_ITEM
        }
    }

    override fun getItemCount(): Int = if (itemList.size == 0) {
        1
    } else {
        itemList.size
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.onBind(itemList[position])
        }
    }

    inner class ItemViewHolder(private val view: View) :
            androidx.recyclerview.widget.RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        fun onBind(data: Category) {
            view.category_title.apply { text = data.name }
            view.category_description.apply { text = data.description }
        }

        override fun onClick(v: View) {
            if (::callback.isInitialized) {
                callback.onItemSelected(adapterPosition)
            }
        }

    }
}