package com.country.tableview.listener.click

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.country.tableview.ITableView
import com.country.tableview.adapter.recyclerview.CellRecyclerView
import com.country.tableview.adapter.recyclerview.CellRowRecyclerViewAdapter
import com.country.tableview.adapter.recyclerview.holder.AbstractViewHolder

class CellRecyclerViewItemClickListener(
        recyclerView: CellRecyclerView,
        tableView: ITableView
) : AbstractItemClickListener(recyclerView, tableView), RecyclerView.OnItemTouchListener {

    private val cellRecyclerView = tableView.cellRecyclerView

    override fun clickAction(view: RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null && gestureDetector.onTouchEvent(e)) {
            val holder = recyclerView.getChildViewHolder(childView) as AbstractViewHolder
            val adapter = recyclerView.adapter as CellRowRecyclerViewAdapter
            val column = holder.adapterPosition
            val row = adapter.yPosition
            if (!tableView.ignoreSelectionColors) {
                selectionHandler.setSelectedCellPositions(holder, column, row)
            }

            tableViewListener?.onCellClicked(holder, column, row)
            return true
        }

        return false
    }

    override fun longPressAction(e: MotionEvent) {
        if (
                recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE ||
                cellRecyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE
        ) {
            return
        }

        val child = recyclerView.findChildViewUnder(e.x, e.y)
        if (child != null && tableViewListener != null) {
            val holder = recyclerView.getChildViewHolder(child)
            val adapter = recyclerView.adapter as CellRowRecyclerViewAdapter
            tableViewListener!!.onCellLongPressed(holder, holder.adapterPosition, adapter.yPosition)
        }
    }
}