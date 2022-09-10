package com.country.tableview.adapter.recyclerview

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import java.util.*

abstract class AbstractRecyclerViewAdapter(
        protected var context: Context,
        items: List<Any>?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: MutableList<Any>? = mutableListOf()
        set(value) {
            if (value != null) {
                field = ArrayList(value)
                notifyDataSetChanged()
            }
        }

    init {
        if (items!!.isNotEmpty()) {
            this.items = items as MutableList<Any>?
        }
    }

    override fun getItemCount(): Int = items!!.size

    fun setItems(itemList: List<Any>, notifyDataSet: Boolean) {
        items = ArrayList(itemList)
        if (notifyDataSet) {
            notifyDataSetChanged()
        }
    }

    fun getItem(position: Int): Any? {
        return if (
                items == null ||
                items!!.isEmpty() ||
                position < 0 ||
                position >= items!!.size
        ) {
            null
        } else {
            items!![position]
        }
    }

    fun deleteItem(position: Int) {
        if (position != NO_POSITION) {
            items!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun deleteItemRange(position: Int, itemCount: Int) {
        (position until position + itemCount + 1)
                .filter { it != NO_POSITION }
                .forEach { items!!.removeAt(it) }
        notifyItemRangeRemoved(position, itemCount)
    }

    fun addItem(position: Int, item: Any?) {
        if (position != NO_POSITION && item != null) {
            items!!.add(position, item)
            notifyItemInserted(position)
        }
    }

    fun addItemRange(position: Int, items: List<*>?) {
        if (items != null) {
            items.indices.forEach { i ->
                if (i != NO_POSITION) {
                    items[i]?.let { this.items!!.add(i + position, it) }
                }
            }

            notifyItemRangeInserted(
                    position,
                    items.size
            )
        }
    }

    fun changeItem(position: Int, item: Any?) {
        if (position != NO_POSITION && item != null) {
            items!![position] = item
            notifyItemChanged(position)
        }
    }

    fun changeItemRange(position: Int, itemList: List<Any>?) {
        if (items!!.size > position + itemList!!.size) {
            itemList.indices
                    .filter { it != NO_POSITION }
                    .forEach { items!![it + position] = itemList[it] }

            notifyItemRangeChanged(
                    position,
                    itemList.size
            )
        }
    }
}