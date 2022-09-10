package com.country.tableview.handler

import androidx.recyclerview.widget.DiffUtil
import com.country.tableview.ITableView
import com.country.tableview.adapter.recyclerview.CellRecyclerViewAdapter
import com.country.tableview.adapter.recyclerview.ColumnHeaderRecyclerViewAdapter
import com.country.tableview.adapter.recyclerview.RowHeaderRecyclerViewAdapter
import com.country.tableview.feature.sort.*
import com.country.tableview.feature.sort.SortState.UNSORTED
import java.util.*
import kotlin.collections.ArrayList

class ColumnSortHandler(tableView: ITableView) {

    private val cellRecyclerViewAdapter =
            tableView.cellRecyclerView.adapter as CellRecyclerViewAdapter

    private val rowHeaderRecyclerViewAdapter =
            tableView.rowHeaderRecyclerView.adapter as RowHeaderRecyclerViewAdapter

    private val columnHeaderRecyclerViewAdapter =
            tableView.columnHeaderRecyclerView.adapter as ColumnHeaderRecyclerViewAdapter

    val rowHeaderSortingStatus: SortState?
        get() = rowHeaderRecyclerViewAdapter.rowHeaderSortHelper!!.sortingStatus

    @Suppress("UNCHECKED_CAST")
    fun sortByRowHeader(sortState: SortState) {
        val originalList = rowHeaderRecyclerViewAdapter.items as MutableList<Sortable>
        val sortedList = ArrayList(originalList) as MutableList<Sortable>
        if (sortState != UNSORTED) {
            Collections.sort(sortedList, RowHeaderSortComparator(sortState))
        }

        rowHeaderRecyclerViewAdapter.rowHeaderSortHelper!!.sortingStatus = sortState
        swapItems(originalList, sortedList)
    }

    @Suppress("UNCHECKED_CAST")
    fun sort(column: Int, sortState: SortState) {
        val originalList = cellRecyclerViewAdapter.items as MutableList<Sortable>
        val sortedList = originalList as ArrayList<List<Sortable>>
        val originalRowHeaderList = rowHeaderRecyclerViewAdapter.items as MutableList<Sortable>
        val sortedRowHeaderList = ArrayList(originalRowHeaderList)
        if (sortState !== UNSORTED) {
            Collections.sort<List<Sortable>>(sortedList, ColumnSortComparator(column, sortState))
            val columnForRowHeaderSortComparator = ColumnForRowHeaderSortComparator(
                    originalRowHeaderList,
                    originalList,
                    column,
                    sortState
            )

            Collections.sort(sortedRowHeaderList, columnForRowHeaderSortComparator)
        }

        columnHeaderRecyclerViewAdapter.columnSortHelper!!.setSortingStatus(column, sortState)
        swapItems(originalList, sortedList, column, sortedRowHeaderList)
    }

    private fun swapItems(oldItems: List<Sortable>, newItems: List<Sortable>) {
        val diffCallback = RowHeaderSortCallback(oldItems, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        rowHeaderRecyclerViewAdapter.setItems(newItems, false)
        diffResult.dispatchUpdatesTo(rowHeaderRecyclerViewAdapter)
        diffResult.dispatchUpdatesTo(cellRecyclerViewAdapter)
    }

    private fun swapItems(
            oldItems: List<List<Sortable>>,
            newItems: List<List<Sortable>>,
            column: Int,
            newRowHeader: List<Sortable>
    ) {
        val diffCallback = ColumnSortCallback(oldItems, newItems, column)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        rowHeaderRecyclerViewAdapter.setItems(newRowHeader, false)
        cellRecyclerViewAdapter.setItems(newItems, false)
        diffResult.dispatchUpdatesTo(rowHeaderRecyclerViewAdapter)
        diffResult.dispatchUpdatesTo(cellRecyclerViewAdapter)
    }

    @Suppress("UNCHECKED_CAST")
    fun swapItems(newItems: List<List<Sortable>>, column: Int) {
        val oldItems = cellRecyclerViewAdapter.items as List<List<Sortable>>
        val diffCallback = ColumnSortCallback(oldItems, newItems, column)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        cellRecyclerViewAdapter.setItems(newItems, false)
        diffResult.dispatchUpdatesTo(rowHeaderRecyclerViewAdapter)
        diffResult.dispatchUpdatesTo(cellRecyclerViewAdapter)
    }

    fun getSortingStatus(column: Int): SortState {
        return columnHeaderRecyclerViewAdapter.columnSortHelper!!.getSortingStatus(column)
    }
}
