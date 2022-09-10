package com.country.tableview.feature.pagination

interface IPagination {

    val currentPage: Int

    var itemsPerPage: Int

    val pageCount: Int

    val isPaginated: Boolean

    var onTableViewPageTurnedListener: Pagination.OnTableViewPageTurnedListener?

    fun loadNextPage()

    fun loadPreviousPage()

    fun loadPage(page: Int)
}