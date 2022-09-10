package com.country.tableview.feature.sort

import com.country.tableview.feature.sort.SortState.DESCENDING
import java.util.*

class ColumnSortComparator(
        private val xPosition: Int,
        sortState: SortState
) : AbstractSortComparator(), Comparator<List<Sortable>> {

    init {
        this.sortState = sortState
    }

    override fun compare(t1: List<Sortable>, t2: List<Sortable>): Int {
        val o1 = t1[xPosition].content
        val o2 = t2[xPosition].content
        return when (sortState) {
            DESCENDING -> compareContent(o2, o1)
            else -> compareContent(o1, o2)
        }
    }
}