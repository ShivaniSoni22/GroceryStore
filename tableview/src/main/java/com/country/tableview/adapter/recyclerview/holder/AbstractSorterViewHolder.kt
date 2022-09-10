package com.country.tableview.adapter.recyclerview.holder

import android.view.View
import com.country.tableview.feature.sort.SortState
import com.country.tableview.feature.sort.SortState.UNSORTED

class AbstractSorterViewHolder(itemView: View) : AbstractViewHolder(itemView) {

    var sortState = UNSORTED
        private set

    fun onSortingStatusChanged(sortState: SortState) {
        this.sortState = sortState
    }
}
