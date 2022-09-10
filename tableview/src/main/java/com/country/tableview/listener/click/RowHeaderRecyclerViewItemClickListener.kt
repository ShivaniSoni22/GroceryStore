package com.country.tableview.listener.click

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.country.tableview.ITableView
import com.country.tableview.adapter.recyclerview.CellRecyclerView
import com.country.tableview.adapter.recyclerview.holder.AbstractViewHolder

class RowHeaderRecyclerViewItemClickListener(
        recyclerView: CellRecyclerView,
        tableView: ITableView
) : AbstractItemClickListener(recyclerView, tableView) {

    override fun clickAction(view: RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null && gestureDetector.onTouchEvent(e)) {
            val holder = recyclerView.getChildViewHolder(childView) as AbstractViewHolder
            val row = holder.adapterPosition
            if (!tableView.ignoreSelectionColors) {
                selectionHandler.setSelectedRowPosition(holder, row)
            }

            tableViewListener?.onRowHeaderClicked(holder, row)
            return true
        }

        return false
    }

    override fun longPressAction(e: MotionEvent) {
        if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
            return
        }

        val child = recyclerView.findChildViewUnder(e.x, e.y)
        if (child != null && tableViewListener != null) {
            val holder = recyclerView.getChildViewHolder(child)
            tableViewListener!!.onRowHeaderLongPressed(holder, holder.adapterPosition)
        }
    }
}
