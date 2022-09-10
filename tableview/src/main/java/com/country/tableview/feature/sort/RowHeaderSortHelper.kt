package com.country.tableview.feature.sort

import com.country.tableview.feature.sort.SortState.UNSORTED

class RowHeaderSortHelper {

    private var sortState: SortState? = null

    val isSorted: Boolean
        get() = sortState != UNSORTED

    var sortingStatus: SortState?
        get() = sortState
        set(status) {
            sortState = status
            sortingStatusChanged(status!!)
        }

    private fun sortingStatusChanged(sortState: SortState) {
        this.sortState = sortState
    }

    fun clearSortingStatus() {
        sortState = UNSORTED
    }
}
