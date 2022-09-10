package com.country.tableview.handler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.country.tableview.ITableView
import com.country.tableview.layoutmanager.ColumnLayoutManager

class ScrollHandler(private val tableView: ITableView) {

    private val cellLayoutManager = tableView.cellLayoutManager

    private val rowHeaderLayoutManager = tableView.rowHeaderLayoutManager

    fun scrollToColumnPosition(column: Int) {
        if (!(tableView as View).isShown) {
            tableView.horizontalRecyclerViewListener.scrollPosition = column
        }

        scrollColumnHeader(column)
        scrollCellHorizontally(column)
    }

    fun scrollToRowPosition(row: Int) {
        rowHeaderLayoutManager.scrollToPosition(row)
        cellLayoutManager.scrollToPosition(row)
    }

    private fun scrollCellHorizontally(columnPosition: Int) {
        val cellLayoutManager = tableView.cellLayoutManager
        (cellLayoutManager.findFirstVisibleItemPosition() until cellLayoutManager
                .findLastVisibleItemPosition() + 1)
                .mapNotNull { cellLayoutManager.findViewByPosition(it) as RecyclerView }
                .map { it.layoutManager as ColumnLayoutManager }
                .forEach { it.scrollToPosition(columnPosition) }
    }

    private fun scrollColumnHeader(column: Int) {
        tableView.columnHeaderLayoutManager.scrollToPosition(column)
    }
}
