package com.country.grocerystore.listeners

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.country.tableview.TableView
import com.country.tableview.listener.ITableViewListener
import com.country.grocerystore.menu.ColumnHeaderLongPressPopup
import com.country.grocerystore.menu.RowHeaderLongPressPopup
import com.country.grocerystore.viewholders.RandomDataColumnHeaderViewHolder
import com.country.grocerystore.viewholders.RandomDataRowHeaderViewHolder

class TableViewListener(private val tableView: TableView) : ITableViewListener {

    private val context: Context = tableView.context

    override fun onCellClicked(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {
    }

    override fun onCellLongPressed(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {
    }

    override fun onColumnHeaderClicked(columnHeaderView: RecyclerView.ViewHolder, column: Int) {
    }

    override fun onColumnHeaderLongPressed(columnHeaderView: RecyclerView.ViewHolder, column: Int) {
        if (columnHeaderView is RandomDataColumnHeaderViewHolder) {
            val popup = ColumnHeaderLongPressPopup(columnHeaderView, tableView)
            popup.show()
        }
    }

    override fun onRowHeaderClicked(rowHeaderView: RecyclerView.ViewHolder, row: Int) {
    }

    override fun onRowHeaderLongPressed(rowHeaderView: RecyclerView.ViewHolder, row: Int) {
        if (rowHeaderView is RandomDataRowHeaderViewHolder) {
            val popup = RowHeaderLongPressPopup(rowHeaderView, tableView)
            popup.show()
        }
    }

}
