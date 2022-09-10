package com.country.tableview.handler

import com.country.tableview.ITableView
import com.country.tableview.adapter.recyclerview.CellRecyclerViewAdapter
import com.country.tableview.adapter.recyclerview.RowHeaderRecyclerViewAdapter
import com.country.tableview.feature.filter.Filter
import com.country.tableview.feature.filter.FilterChangedListener
import com.country.tableview.feature.filter.FilterType
import com.country.tableview.feature.filter.Filterable
import com.country.tableview.listener.data.AdapterDataSetChangedListener
import java.util.*

class FilterHandler(tableView: ITableView) {

    private val rowHeaderRecyclerViewAdapter: RowHeaderRecyclerViewAdapter

    private val cellRecyclerViewAdapter: CellRecyclerViewAdapter

    private var filteredCellList: MutableList<List<Filterable>>? = null

    private var originalCellDataStore: List<List<Filterable>>? = null

    private var originalCellData: List<List<Filterable>>? = null

    private var filteredRowList: MutableList<Any>? = null

    private var originalRowDataStore: List<Any>? = null

    private var originalRowData: List<Any>? = null

    private var filterChangedListeners: MutableList<FilterChangedListener> = ArrayList()

    @Suppress("UNCHECKED_CAST")
    private val adapterDataSetChangedListener = object : AdapterDataSetChangedListener() {
        override fun onRowHeaderItemsChanged(rowHeaderItems: List<Any>?) {
            if (rowHeaderItems != null) {
                originalRowDataStore = ArrayList(rowHeaderItems)
            }
        }

        override fun onCellItemsChanged(cellItems: List<Any>?) {
            if (cellItems != null) {
                originalCellDataStore = ArrayList(cellItems) as List<List<Filterable>>
            }
        }
    }

    init {
        tableView.adapter!!.addAdapterDataSetChangedListener(adapterDataSetChangedListener)
        cellRecyclerViewAdapter = tableView.cellRecyclerView.adapter as CellRecyclerViewAdapter
        rowHeaderRecyclerViewAdapter =
                tableView.rowHeaderRecyclerView.adapter as RowHeaderRecyclerViewAdapter
    }

    fun filter(filter: Filter) {
        if (originalCellDataStore == null || originalRowDataStore == null) {
            return
        }

        originalCellData = ArrayList<List<Filterable>>(originalCellDataStore!!)
        originalRowData = ArrayList(originalRowDataStore!!)
        filteredCellList = ArrayList()
        filteredRowList = ArrayList()

        if (filter.filterItems.isEmpty()) {
            filteredCellList = ArrayList(originalCellDataStore!!)
            filteredRowList = ArrayList(originalRowDataStore!!)
            dispatchFilterClearedToListeners(
                    originalCellDataStore as List<List<Filterable>>,
                    originalRowDataStore as List<Any>
            )
        } else {
            var x = 0
            while (x < filter.filterItems.size) {
                val filterItem = filter.filterItems[x]
                if (filterItem.filterType == FilterType.ALL) {
                    for (itemsList in originalCellData!!) {
                        for (item in itemsList) {
                            if (item
                                            .filterableKeyword
                                            .toLowerCase()
                                            .contains(filterItem
                                                    .filter
                                                    .toLowerCase())) {
                                filteredCellList!!.add(itemsList)
                                filteredRowList!!.add(
                                        originalRowData!![filteredCellList!!.indexOf(itemsList)]
                                )
                                break
                            }
                        }
                    }
                } else {
                    for (itemsList in originalCellData!!) {
                        if (itemsList[filterItem.column]
                                        .filterableKeyword
                                        .toLowerCase()
                                        .contains(filterItem
                                                .filter
                                                .toLowerCase())) {
                            filteredCellList!!.add(itemsList)
                            filteredRowList!!.add(
                                    originalRowData!![filteredCellList!!.indexOf(itemsList)]
                            )
                        }
                    }
                }

                // If this is the last filter to be processed,
                // the filtered lists will not be cleared
                if (++x < filter.filterItems.size) {
                    originalCellData = ArrayList<List<Filterable>>(filteredCellList!!)
                    originalRowData = ArrayList(filteredRowList!!)
                    filteredCellList!!.clear()
                    filteredRowList!!.clear()
                }
            }
        }

        // Sets the filtered data to the TableView
        rowHeaderRecyclerViewAdapter.setItems(filteredRowList as List<Any>, true)
        cellRecyclerViewAdapter.setItems(filteredCellList as List<Any>, true)

        // Tells the listeners that the TableView has been filtered
        dispatchFilterChangedToListeners(
                filteredCellList as List<List<Filterable>>,
                filteredRowList as List<Any>
        )
    }

    private fun dispatchFilterChangedToListeners(
            filteredCellItems: List<List<Filterable>>,
            filteredRowHeaderItems: List<Any>
    ) {
        filterChangedListeners.forEach {
            it.onFilterChanged(
                    filteredCellItems,
                    filteredRowHeaderItems
            )
        }
    }

    private fun dispatchFilterClearedToListeners(
            originalCellItems: List<List<Filterable>>,
            originalRowHeaderItems: List<Any>
    ) {
        filterChangedListeners.forEach {
            it.onFilterCleared(
                    originalCellItems,
                    originalRowHeaderItems
            )
        }
    }

    fun addFilterChangedListener(listener: FilterChangedListener) {
        filterChangedListeners.add(listener)
    }
}
