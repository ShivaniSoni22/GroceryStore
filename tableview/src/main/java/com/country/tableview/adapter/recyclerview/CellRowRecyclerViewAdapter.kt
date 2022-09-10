package com.country.tableview.adapter.recyclerview

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.country.tableview.adapter.ITableAdapter
import com.country.tableview.adapter.recyclerview.holder.AbstractViewHolder

class CellRowRecyclerViewAdapter(
        context: Context,
        items: List<Any>,
        private val tableAdapter: ITableAdapter,
        var yPosition: Int
) : AbstractRecyclerViewAdapter(context, items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return tableAdapter.onCreateCellViewHolder(parent, viewType) as AbstractViewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, xPosition: Int) {
        val viewHolder = holder as AbstractViewHolder
        val value = getItem(xPosition)
        tableAdapter.onBindCellViewHolder(viewHolder, value!!, xPosition, yPosition)
    }

    override fun getItemViewType(position: Int): Int {
        return tableAdapter.getCellItemViewType(position)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        val viewHolder = holder as AbstractViewHolder
        val selectionState = tableAdapter.tableView!!.selectionHandler
                .getCellSelectionState(holder.adapterPosition, yPosition)
        if (!tableAdapter.tableView!!.ignoreSelectionColors) {
            if (selectionState === AbstractViewHolder.SelectionState.SELECTED) {
                viewHolder.setBackgroundColor(tableAdapter.tableView!!.selectedColor)
            } else {
                viewHolder.setBackgroundColor(tableAdapter.tableView!!.unselectedColor)
            }
        }

        viewHolder.setSelected(selectionState)
    }
}
