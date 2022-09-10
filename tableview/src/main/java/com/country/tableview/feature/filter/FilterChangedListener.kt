package com.country.tableview.feature.filter

abstract class FilterChangedListener {

    open fun onFilterChanged(
            filteredCellItems: List<List<Any>>,
            filteredRowHeaderItems: List<Any>
    ) {
    }

    open fun onFilterCleared(
            originalCellItems: List<List<Any>>,
            originalRowHeaderItems: List<Any>
    ) {
    }
}
