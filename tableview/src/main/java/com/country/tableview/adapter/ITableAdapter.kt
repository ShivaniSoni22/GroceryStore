package com.country.tableview.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.country.tableview.ITableView
import com.country.tableview.adapter.recyclerview.CellRecyclerViewAdapter
import com.country.tableview.adapter.recyclerview.ColumnHeaderRecyclerViewAdapter
import com.country.tableview.adapter.recyclerview.RowHeaderRecyclerViewAdapter
import com.country.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.country.tableview.listener.data.AdapterDataSetChangedListener

interface ITableAdapter {

    var cellItems: List<List<Any>>?

    var columnHeaderItems: List<Any>?

    var rowHeaderItems: List<Any>?

    var cellRecyclerViewAdapter: CellRecyclerViewAdapter?

    var columnHeaderRecyclerViewAdapter: ColumnHeaderRecyclerViewAdapter?

    var rowHeaderRecyclerViewAdapter: RowHeaderRecyclerViewAdapter?

    var cornerView: View?

    var tableView: ITableView?

    var columnHeaderHeight: Int?

    var rowHeaderWidth: Int?

    var dataSetChangedListeners: MutableList<AdapterDataSetChangedListener>

    fun getColumnHeaderItemViewType(column: Int): Int

    fun getRowHeaderItemViewType(row: Int): Int

    fun getCellItemViewType(column: Int): Int

    fun onCreateCellViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    fun onBindCellViewHolder(
            holder: AbstractViewHolder,
            cellItem: Any,
            column: Int,
            row: Int
    )

    fun onCreateColumnHeaderViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    fun onBindColumnHeaderViewHolder(
            holder: AbstractViewHolder,
            columnHeaderItem: Any,
            column: Int
    )

    fun onCreateRowHeaderViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    fun onBindRowHeaderViewHolder(
            holder: AbstractViewHolder,
            rowHeaderItem: Any,
            row: Int
    )

    fun onCreateCornerView(): View?

    fun addAdapterDataSetChangedListener(listener: AdapterDataSetChangedListener)
}
