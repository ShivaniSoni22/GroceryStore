package com.country.tableview.adapter.recyclerview

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.country.tableview.adapter.ITableAdapter
import com.country.tableview.adapter.recyclerview.holder.AbstractSorterViewHolder
import com.country.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.country.tableview.feature.sort.ColumnSortHelper

class ColumnHeaderRecyclerViewAdapter(
        context: Context,
        items: List<Any>?,
        private val tableAdapter: ITableAdapter
) : AbstractRecyclerViewAdapter(context, items) {

    var columnSortHelper: ColumnSortHelper? = null
        get() {
            if (field == null) {
                field = ColumnSortHelper(tableAdapter.tableView!!.columnHeaderLayoutManager)
            }

            return field
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return tableAdapter.onCreateColumnHeaderViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as AbstractViewHolder
        val value = getItem(position)
        tableAdapter.onBindColumnHeaderViewHolder(viewHolder, value!!, position)
    }

    override fun getItemViewType(position: Int): Int {
        return tableAdapter.getColumnHeaderItemViewType(position)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        val viewHolder = holder as AbstractViewHolder
        val selectionState = tableAdapter.tableView!!.selectionHandler
                .getColumnSelectionState(viewHolder.adapterPosition)

        // Update selection colors
        if (!tableAdapter.tableView!!.ignoreSelectionColors) {
            tableAdapter.tableView!!.selectionHandler
                    .changeColumnBackgroundColorBySelectionStatus(viewHolder, selectionState)
        }

        // Update selection status
        viewHolder.setSelected(selectionState)

        // Determine if TableView is sorted or not
        if (tableAdapter.tableView!!.isSorted) {
            if (viewHolder is AbstractSorterViewHolder) {
                val state = columnSortHelper!!.getSortingStatus(viewHolder.getAdapterPosition())
                viewHolder.onSortingStatusChanged(state)
            }
        }
    }
}
