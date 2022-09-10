package com.country.tableview.listener.data

abstract class AdapterDataSetChangedListener {

    open fun onColumnHeaderItemsChanged(columnHeaderItems: List<Any>?) {}

    open fun onRowHeaderItemsChanged(rowHeaderItems: List<Any>?) {}

    open fun onCellItemsChanged(cellItems: List<Any>?) {}

    open fun onDataSetChanged(
            columnHeaderItems: List<Any>?,
            rowHeaderItems: List<Any>?,
            cellItems: List<Any>?
    ) {
    }
}
