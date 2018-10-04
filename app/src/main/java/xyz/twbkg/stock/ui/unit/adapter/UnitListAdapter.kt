package xyz.twbkg.stock.ui.unit.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_unit.view.*
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.AdapterListAdapterCallback
import xyz.twbkg.stock.application.BaseRecyclerView
import xyz.twbkg.stock.application.EmptyViewHolder
import xyz.twbkg.stock.data.model.db.UnitMeasure


class UnitListAdapter : BaseRecyclerView<UnitMeasure>() {


    private var itemList: ArrayList<UnitMeasure> = arrayListOf()
    private lateinit var callback: AdapterListAdapterCallback

    private val TYPE_ITEM = R.layout.item_unit
    private val TYPE_EMPTY = R.layout.item_empty

    override fun addCallback(callback: AdapterListAdapterCallback) {
        this.callback = callback
    }

    override fun addItem(item: UnitMeasure) {
        this.itemList.add(item)
        notifyItemInserted(itemList.size - 1)
    }

    override fun addItems(items: List<UnitMeasure>) {
        this.itemList.clear()
        this.itemList.addAll(items)
        val afterAdd = this.itemList.size
        notifyDataSetChanged()
//        val beforeAdd = this.itemList.size
//        this.itemList.addAll(items)
//        val afterAdd = this.itemList.size
//        notifyItemRangeInserted(beforeAdd, afterAdd)
    }

    override fun updateItems(items: List<UnitMeasure>) {
        this.itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun updateItem(position: Int, item: UnitMeasure) {
        this.itemList[position] = item
        notifyItemChanged(position)
    }

    override fun emptyItem() {
        this.itemList.clear()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.onBind(itemList[position])
        }
    }

    inner class ItemViewHolder(private val view: View) :
            RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        fun onBind(data: UnitMeasure) {
            view.unit_title.apply { text = data.name }
            view.unit_description.apply { text = data.description }
        }

        override fun onClick(v: View) {
            if (::callback.isInitialized) {
                callback.onItemSelected(adapterPosition)
            }
        }

    }
}