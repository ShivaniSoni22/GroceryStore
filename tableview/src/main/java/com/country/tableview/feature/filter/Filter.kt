package com.country.tableview.feature.filter

import android.text.TextUtils
import com.country.tableview.ITableView
import com.country.tableview.feature.filter.FilterType.ALL
import com.country.tableview.feature.filter.FilterType.COLUMN

class Filter(
        private val tableView: ITableView
) {

    var filterItems: MutableList<FilterItem> = ArrayList()
        private set

    fun set(filter: String) {
        set(-1, filter)
    }

    fun set(column: Int, filter: String) {
        val filterItem = FilterItem(
                if (column == -1) ALL else COLUMN,
                column,
                filter
        )

        if (isAlreadyFiltering(column, filterItem)) {
            if (TextUtils.isEmpty(filter)) {
                remove(column, filterItem)
            } else {
                update(column, filterItem)
            }
        } else if (!TextUtils.isEmpty(filter)) {
            add(filterItem)
        }
    }

    private fun add(filterItem: FilterItem) {
        filterItems.add(filterItem)
        tableView.filter(this)
    }

    private fun remove(column: Int, filterItem: FilterItem) {
        val filterItemIterator = filterItems.iterator()
        while (filterItemIterator.hasNext()) {
            val item = filterItemIterator.next()
            if (column == -1 && item.filterType == filterItem.filterType) {
                filterItemIterator.remove()
                break
            } else if (item.column == filterItem.column) {
                filterItemIterator.remove()
                break
            }
        }

        tableView.filter(this)
    }

    private fun update(column: Int, filterItem: FilterItem) {
        val filterItemIterator = filterItems.iterator()
        while (filterItemIterator.hasNext()) {
            val item = filterItemIterator.next()
            if (column == -1 && item.filterType == filterItem.filterType) {
                filterItems[filterItems.indexOf(item)] = filterItem
                break
            } else if (item.column == filterItem.column) {
                filterItems[filterItems.indexOf(item)] = filterItem
                break
            }
        }

        tableView.filter(this)
    }

    private fun isAlreadyFiltering(column: Int, filterItem: FilterItem): Boolean {
        for (item in filterItems) {
            if (column == -1 && item.filterType == filterItem.filterType) {
                return true
            } else if (item.column == filterItem.column) {
                return true
            }
        }

        return false
    }
}
