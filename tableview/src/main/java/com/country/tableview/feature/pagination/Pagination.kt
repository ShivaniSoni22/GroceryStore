package com.country.tableview.feature.pagination

import com.country.tableview.ITableView
import com.country.tableview.adapter.recyclerview.CellRecyclerViewAdapter
import com.country.tableview.adapter.recyclerview.RowHeaderRecyclerViewAdapter
import com.country.tableview.feature.filter.FilterChangedListener
import com.country.tableview.listener.data.AdapterDataSetChangedListener
import java.util.*

@Suppress("UNCHECKED_CAST")
class Pagination @JvmOverloads constructor(
        tableView: ITableView,
        numItemsPerPage: Int = DEFAULT_ITEMS_PER_PAGE,
        listener: OnTableViewPageTurnedListener? = null
) : IPagination {

    override var currentPage: Int = 0
        private set

    override var itemsPerPage: Int = 0
        set(value) {
            field = value
            currentPage = 1
            reloadPages()
        }

    override var pageCount: Int = 0
        private set

    override val isPaginated: Boolean
        get() = itemsPerPage > 0

    override var onTableViewPageTurnedListener: OnTableViewPageTurnedListener? = null

    private var rowHeaderRecyclerViewAdapter: RowHeaderRecyclerViewAdapter? = null

    private var cellRecyclerViewAdapter: CellRecyclerViewAdapter? = null

    private var currentPageCellData: MutableList<List<Any>>? = null

    private var currentPageRowData: MutableList<Any>? = null

    private var originalCellData: List<List<Any>>? = null

    private var originalRowData: List<Any>? = null

    private val adapterDataSetChangedListener = object : AdapterDataSetChangedListener() {
        override fun onRowHeaderItemsChanged(rowHeaderItems: List<Any>?) {
            if (rowHeaderItems != null) {
                originalRowData = ArrayList(rowHeaderItems)
                reloadPages()
            }
        }

        override fun onCellItemsChanged(cellItems: List<Any>?) {
            if (cellItems != null) {
                originalCellData = ArrayList(cellItems) as List<List<Any>>
                reloadPages()
            }
        }
    }

    private val filterChangedListener = object : FilterChangedListener() {
        override fun onFilterChanged(
                filteredCellItems: List<List<Any>>,
                filteredRowHeaderItems: List<Any>
        ) {
            originalCellData = ArrayList(filteredCellItems)
            originalRowData = ArrayList(filteredRowHeaderItems)
            reloadPages()
        }

        override fun onFilterCleared(
                originalCellItems: List<List<Any>>,
                originalRowHeaderItems: List<Any>
        ) {
            originalCellData = ArrayList(originalCellItems)
            originalRowData = ArrayList(originalRowHeaderItems)
            reloadPages()
        }
    }

    init {
        onTableViewPageTurnedListener = listener
        rowHeaderRecyclerViewAdapter = tableView
                .rowHeaderRecyclerView.adapter as RowHeaderRecyclerViewAdapter
        cellRecyclerViewAdapter = tableView
                .cellRecyclerView.adapter as CellRecyclerViewAdapter
        tableView.adapter!!.addAdapterDataSetChangedListener(adapterDataSetChangedListener)
        tableView.filterHandler.addFilterChangedListener(filterChangedListener)
        originalCellData = cellRecyclerViewAdapter!!.items as List<List<Any>>
        originalRowData = rowHeaderRecyclerViewAdapter!!.items as List<Any>
        itemsPerPage = numItemsPerPage
    }

    private fun reloadPages() {
        if (originalCellData != null && originalRowData != null) {
            paginateData()
            loadPage(currentPage)
        }
    }

    private fun paginateData() {
        val start: Int
        val end: Int
        currentPageCellData = ArrayList()
        currentPageRowData = ArrayList()
        if (itemsPerPage == 0) {
            currentPageCellData!!.addAll(originalCellData!!)
            currentPageRowData!!.addAll(originalRowData!!)
            pageCount = 1
            start = 0
            end = currentPageCellData!!.size
        } else {
            start = currentPage * itemsPerPage - itemsPerPage
            end = if (currentPage * itemsPerPage > originalCellData!!.size)
                originalCellData!!.size
            else
                currentPage * itemsPerPage

            (start until end).forEach { x ->
                currentPageCellData!!.add(originalCellData!![x])
                currentPageRowData!!.add(originalRowData!![x])
            }

            pageCount = Math.ceil(originalCellData!!.size.toDouble() / itemsPerPage).toInt()
        }

        rowHeaderRecyclerViewAdapter!!.setItems(currentPageRowData as List<Any>, true)
        cellRecyclerViewAdapter!!.setItems(currentPageCellData as List<Any>, true)
        if (onTableViewPageTurnedListener != null) {
            onTableViewPageTurnedListener!!.onPageTurned(currentPageCellData!!.size, start, end - 1)
        }
    }

    override fun loadNextPage() {
        currentPage = if (currentPage + 1 > pageCount) currentPage else ++currentPage
        paginateData()
    }

    override fun loadPreviousPage() {
        currentPage = if (currentPage - 1 == 0) currentPage else --currentPage
        paginateData()
    }

    override fun loadPage(page: Int) {
        currentPage = if (page > pageCount || page < 1) if (pageCount in 1..(page - 1)) pageCount else currentPage else page
        paginateData()
    }

    interface OnTableViewPageTurnedListener {

        fun onPageTurned(numItems: Int, itemsStart: Int, itemsEnd: Int)
    }

    companion object {
        private const val DEFAULT_ITEMS_PER_PAGE = 10
    }
}
