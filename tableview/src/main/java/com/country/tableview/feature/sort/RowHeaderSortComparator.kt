package com.country.tableview.feature.sort

import com.country.tableview.feature.sort.SortState.DESCENDING
import java.util.*

class RowHeaderSortComparator(
        sortState: SortState
) : AbstractSortComparator(), Comparator<Sortable> {

    init {
        this.sortState = sortState
    }

    override fun compare(o1: Sortable, o2: Sortable): Int {
        return when {
            sortState === DESCENDING -> compareContent(o2.content, o1.content)
            else -> compareContent(o1.content, o2.content)
        }
    }
}
