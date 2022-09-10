package com.country.tableview.listener.layout

import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.country.tableview.ITableView
import com.country.tableview.adapter.recyclerview.CellRecyclerView
import com.country.tableview.layoutmanager.CellLayoutManager

class TableViewLayoutChangeListener(tableView: ITableView) : View.OnLayoutChangeListener {

    private val cellRecyclerView: CellRecyclerView = tableView.cellRecyclerView

    private val columnHeaderRecyclerView: CellRecyclerView = tableView.columnHeaderRecyclerView

    private val cellLayoutManager: CellLayoutManager = tableView.cellLayoutManager

    override fun onLayoutChange(
            v: View,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
    ) {
        if (v.isShown && right - left != oldRight - oldLeft) {
            if (columnHeaderRecyclerView.width > cellRecyclerView.width) {
                cellLayoutManager.remeasureAllChild()
            } else if (cellRecyclerView.width > columnHeaderRecyclerView.width) {
                columnHeaderRecyclerView.layoutParams.width = WRAP_CONTENT
                columnHeaderRecyclerView.requestLayout()
            }
        }
    }
}
