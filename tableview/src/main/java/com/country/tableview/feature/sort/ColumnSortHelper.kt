package com.country.tableview.feature.sort

import com.country.tableview.adapter.recyclerview.holder.AbstractSorterViewHolder
import com.country.tableview.feature.sort.SortState.UNSORTED
import com.country.tableview.layoutmanager.ColumnHeaderLayoutManager
import java.util.*

class ColumnSortHelper(private val columnHeaderLayoutManager: ColumnHeaderLayoutManager) {

    private val sortedColumns = ArrayList<Directive>()

    val isSorted: Boolean
        get() = sortedColumns.size != 0

    private fun sortingStatusChanged(column: Int, sortState: SortState) {
        val holder = columnHeaderLayoutManager.getViewHolder(column)
        when (holder) {
            is AbstractSorterViewHolder -> {
                holder.onSortingStatusChanged(sortState)
            }

            else -> {
//                throw TableViewSortException()
            }
        }
    }

    private fun getDirective(column: Int): Directive {
        return sortedColumns.indices
                .map { sortedColumns[it] }
                .firstOrNull { it.column == column }
                ?: EMPTY_DIRECTIVE
    }

    fun setSortingStatus(column: Int, status: SortState) {
        val directive = getDirective(column)
        if (directive !== EMPTY_DIRECTIVE) {
            sortedColumns.remove(directive)
        }

        if (status !== UNSORTED) {
            sortedColumns.add(Directive(column, status))
        }

        sortingStatusChanged(column, status)
    }

    fun clearSortingStatus() {
        sortedColumns.clear()
    }

    fun getSortingStatus(column: Int): SortState {
        return getDirective(column).direction
    }

    private data class Directive(val column: Int, val direction: SortState)

    companion object {
        private val EMPTY_DIRECTIVE = Directive(-1, UNSORTED)
    }
}
