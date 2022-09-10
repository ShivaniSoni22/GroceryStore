package com.country.tableview.feature.sort

import java.util.*

class ColumnForRowHeaderSortComparator(
        private val rowHeaderList: List<Sortable>,
        private val referenceList: List<List<Sortable>>,
        private val column: Int,
        private val sortState: SortState
) : Comparator<Any> {

    private val columnSortComparator = ColumnSortComparator(column, sortState)

    override fun compare(o: Any, t1: Any): Int {
        val o1 = referenceList[rowHeaderList.indexOf(o)][column].content
        val o2 = referenceList[rowHeaderList.indexOf(t1)][column].content
        return when (sortState) {
            SortState.DESCENDING -> columnSortComparator.compareContent(o2, o1)
            else -> columnSortComparator.compareContent(o1, o2)
        }
    }
}
