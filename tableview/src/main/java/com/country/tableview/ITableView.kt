package com.country.tableview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.country.tableview.adapter.AbstractTableAdapter
import com.country.tableview.adapter.recyclerview.CellRecyclerView
import com.country.tableview.feature.filter.Filter
import com.country.tableview.feature.sort.SortState
import com.country.tableview.handler.*
import com.country.tableview.layoutmanager.CellLayoutManager
import com.country.tableview.layoutmanager.ColumnHeaderLayoutManager
import com.country.tableview.listener.ITableViewListener
import com.country.tableview.listener.click.ColumnHeaderRecyclerViewItemClickListener
import com.country.tableview.listener.click.RowHeaderRecyclerViewItemClickListener
import com.country.tableview.listener.scroll.HorizontalRecyclerViewListener
import com.country.tableview.listener.scroll.VerticalRecyclerViewListener

interface ITableView {

    // Boolean attributes

    var showHorizontalSeparators: Boolean

    var showVerticalSeparators: Boolean

    var ignoreSelectionColors: Boolean

    var hasFixedWidth: Boolean

    var isSorted: Boolean

    // Dimensions attributes

    var columnHeaderHeight: Int

    var rowHeaderWidth: Int

    // Colors attributes

    var unselectedColor: Int

    var separatorColor: Int

    var selectedColor: Int

    var shadowColor: Int

    // Handlers

    var visibilityHandler: VisibilityHandler

    var columnSortHandler: ColumnSortHandler

    var selectionHandler: SelectionHandler

    var scrollHandler: ScrollHandler

    var filterHandler: FilterHandler

    // Listeners

    var columnHeaderRecyclerViewItemClickListener: ColumnHeaderRecyclerViewItemClickListener

    var rowHeaderRecyclerViewItemClickListener: RowHeaderRecyclerViewItemClickListener

    var horizontalRecyclerViewListener: HorizontalRecyclerViewListener

    var verticalRecyclerViewListener: VerticalRecyclerViewListener

    var tableViewListener: ITableViewListener?

    // TableView fields

    var columnHeaderRecyclerView: CellRecyclerView

    var rowHeaderRecyclerView: CellRecyclerView

    var cellRecyclerView: CellRecyclerView

    var columnHeaderLayoutManager: ColumnHeaderLayoutManager

    var rowHeaderLayoutManager: LinearLayoutManager

    var cellLayoutManager: CellLayoutManager

    var horizontalItemDecoration: DividerItemDecoration

    var verticalItemDecoration: DividerItemDecoration

    var adapter: AbstractTableAdapter?

    var rowHeaderSortState: SortState

    // Item selection

    var selectedColumn: Int?

    var selectedRow: Int?

    // TableView methods

    fun addView(child: View, params: ViewGroup.LayoutParams)

    fun scrollToColumn(column: Int)

    fun scrollToRow(row: Int)

    fun showColumn(column: Int)

    fun hideColumn(column: Int)

    fun showRow(row: Int)

    fun hideRow(row: Int)

    fun clearHiddenColumnList()

    fun clearHiddenRowList()

    fun showAllHiddenColumns()

    fun showAllHiddenRows()

    fun sortColumn(column: Int, sortState: SortState)

    fun sortRowHeader(sortState: SortState)

    fun remeasureColumnWidth(column: Int)

    fun filter(filter: Filter)

    fun getColumnSortState(column: Int): SortState

    fun isColumnVisible(column: Int): Boolean

    fun isRowVisible(row: Int): Boolean
}