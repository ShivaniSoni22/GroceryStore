package com.country.tableview.listener

import androidx.recyclerview.widget.RecyclerView

interface ITableViewListener {

    fun onColumnHeaderLongPressed(columnHeaderView: RecyclerView.ViewHolder, column: Int)

    fun onCellLongPressed(cellView: RecyclerView.ViewHolder, column: Int, row: Int)

    fun onRowHeaderLongPressed(rowHeaderView: RecyclerView.ViewHolder, row: Int)

    fun onColumnHeaderClicked(columnHeaderView: RecyclerView.ViewHolder, column: Int)

    fun onCellClicked(cellView: RecyclerView.ViewHolder, column: Int, row: Int)

    fun onRowHeaderClicked(rowHeaderView: RecyclerView.ViewHolder, row: Int)
}
