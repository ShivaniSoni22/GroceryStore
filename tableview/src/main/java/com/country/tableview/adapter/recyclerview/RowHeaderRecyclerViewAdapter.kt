package com.country.tableview.adapter.recyclerview

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.country.tableview.adapter.ITableAdapter
import com.country.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.country.tableview.feature.sort.RowHeaderSortHelper

class RowHeaderRecyclerViewAdapter(
        context: Context,
        items: List<Any>?,
        private val tableAdapter: ITableAdapter
) : AbstractRecyclerViewAdapter(context, items) {

    var rowHeaderSortHelper: RowHeaderSortHelper? = null
        get() {
            if (field == null) {
                field = RowHeaderSortHelper()
            }

            return field
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            tableAdapter.onCreateRowHeaderViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as AbstractViewHolder
        val value = getItem(position)
        tableAdapter.onBindRowHeaderViewHolder(viewHolder, value!!, position)
    }

    override fun getItemViewType(position: Int): Int =
            tableAdapter.getRowHeaderItemViewType(position)

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        val viewHolder = holder as AbstractViewHolder
        val selectionState = tableAdapter.tableView!!.selectionHandler
                .getRowSelectionState(holder.adapterPosition)

        // Update selection colors
        if (!tableAdapter.tableView!!.ignoreSelectionColors) {
            tableAdapter.tableView!!.selectionHandler
                    .changeRowBackgroundColorBySelectionStatus(viewHolder, selectionState)
        }

        // Update selection status
        viewHolder.setSelected(selectionState)
    }
}
