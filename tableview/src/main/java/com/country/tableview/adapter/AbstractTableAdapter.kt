package com.country.tableview.adapter

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.country.tableview.ITableView
import com.country.tableview.adapter.recyclerview.CellRecyclerViewAdapter
import com.country.tableview.adapter.recyclerview.ColumnHeaderRecyclerViewAdapter
import com.country.tableview.adapter.recyclerview.RowHeaderRecyclerViewAdapter
import com.country.tableview.listener.data.AdapterDataSetChangedListener

abstract class AbstractTableAdapter(context: Context) : ITableAdapter {

    @Suppress("UNCHECKED_CAST")
    final override var cellItems: List<List<Any>>? = listOf()
        set(value) {
            field = value
            cellRecyclerViewAdapter?.items = value as MutableList<Any>?
            value?.let { dispatchCellDataSetChangesToListeners(it) }
        }

    final override var columnHeaderItems: List<Any>? = listOf()
        set(value) {
            field = value
            columnHeaderRecyclerViewAdapter?.items = value as MutableList<Any>?
            value?.let { dispatchColumnHeaderDataSetChangesToListeners(it) }
        }

    final override var rowHeaderItems: List<Any>? = listOf()
        set(value) {
            field = value
            rowHeaderRecyclerViewAdapter?.items = value as MutableList<Any>?
            value?.let { dispatchRowHeaderDataSetChangesToListeners(it) }
        }

    final override var cellRecyclerViewAdapter: CellRecyclerViewAdapter? = null

    final override var columnHeaderRecyclerViewAdapter: ColumnHeaderRecyclerViewAdapter? = null

    final override var rowHeaderRecyclerViewAdapter: RowHeaderRecyclerViewAdapter? = null

    override var cornerView: View? = null

    override var tableView: ITableView? = null

    override var columnHeaderHeight: Int? = 0

    override var rowHeaderWidth: Int? = 0

    override var dataSetChangedListeners: MutableList<AdapterDataSetChangedListener> = mutableListOf()

    init {
        // Create RecyclerView adapters for cells, row headers and column headers
        cellRecyclerViewAdapter = CellRecyclerViewAdapter(context, cellItems, this)
        columnHeaderRecyclerViewAdapter = ColumnHeaderRecyclerViewAdapter(context, columnHeaderItems, this)
        rowHeaderRecyclerViewAdapter = RowHeaderRecyclerViewAdapter(context, rowHeaderItems, this)
    }

    override fun addAdapterDataSetChangedListener(listener: AdapterDataSetChangedListener) {
        dataSetChangedListeners.add(listener)
    }

    fun setAllItems(
            cellItems: List<List<Any>>,
            columnHeaderItems: List<Any>,
            rowHeaderItems: List<Any>
    ) {
        this.cellItems = cellItems
        this.columnHeaderItems = columnHeaderItems
        this.rowHeaderItems = rowHeaderItems

        // CornerView control
        if (!(columnHeaderItems.isEmpty() || rowHeaderItems.isEmpty() ||
                        cellItems.isEmpty() || tableView == null || cornerView != null)) {
            cornerView = onCreateCornerView()
            if (cornerView != null) {
                tableView!!.addView(
                        cornerView!!,
                        FrameLayout.LayoutParams(
                                rowHeaderWidth!!,
                                columnHeaderHeight!!
                        )
                )
            }
        } else {
            if (cornerView != null) {
                if (!rowHeaderItems.isEmpty()) {
                    cornerView!!.visibility = View.VISIBLE
                } else {
                    cornerView!!.visibility = View.GONE
                }
            }
        }

    }

    fun getCellItem(position: Int): Any? = cellItems?.getOrNull(position)

    fun getColumnHeaderItem(position: Int): Any? = columnHeaderItems?.getOrNull(position)

    fun getRowHeaderItem(position: Int): Any? = rowHeaderItems?.getOrNull(position)

    fun getRowCellItems(position: Int): Any? = cellRecyclerViewAdapter?.getItem(position)

    fun getColumnCellItems(position: Int): Any? = cellRecyclerViewAdapter?.getColumnItems(position)

    fun addRow(position: Int, rowHeaderItem: Any?, cellItems: List<Any?>) {
        cellRecyclerViewAdapter?.addItem(position, cellItems)
        rowHeaderRecyclerViewAdapter?.addItem(position, rowHeaderItem)
    }

    fun addRowRange(position: Int, rowHeaderItems: List<Any>, cellItems: List<List<Any>>) {
        cellRecyclerViewAdapter?.addItemRange(position, cellItems)
        rowHeaderRecyclerViewAdapter?.addItem(position, rowHeaderItems)
    }

    fun addColumn(position: Int, columnHeaderItem: Any?, cellItems: List<Any?>) {
        columnHeaderRecyclerViewAdapter?.addItem(position, columnHeaderItem)
        cellRecyclerViewAdapter?.addColumnItems(position, cellItems)
    }

    fun removeRow(rowPosition: Int) {
        cellRecyclerViewAdapter?.deleteItem(rowPosition)
        rowHeaderRecyclerViewAdapter?.deleteItem(rowPosition)
    }

    fun removeRow(position: Int, count: Int) {
        cellRecyclerViewAdapter?.deleteItemRange(position, count)
        rowHeaderRecyclerViewAdapter?.deleteItemRange(position, count)
    }

    fun removeColumn(position: Int) {
        columnHeaderRecyclerViewAdapter?.deleteItem(position)
        cellRecyclerViewAdapter?.removeColumnItems(position)
    }

    fun changeColumnHeader(position: Int, columnHeaderItem: Any) {
        columnHeaderRecyclerViewAdapter?.changeItem(position, columnHeaderItem)
    }

    fun changeColumnHeaderRange(position: Int, columnHeaderItems: List<Any>) {
        columnHeaderRecyclerViewAdapter?.changeItem(position, columnHeaderItems)
    }

    fun changeRowHeaderItem(position: Int, rowHeaderItem: Any?) {
        rowHeaderRecyclerViewAdapter?.changeItem(position, rowHeaderItem)
    }

    fun changeRowHeaderItemRange(position: Int, rowHeaderItems: List<Any>?) {
        rowHeaderRecyclerViewAdapter?.changeItem(position, rowHeaderItems)
    }

    @Suppress("UNCHECKED_CAST")
    fun changeCellItem(column: Int, row: Int, cellItem: Any) {
        val cells = cellRecyclerViewAdapter?.getItem(row) as MutableList<Any?>
        if (cells.size > column) {
            cells[column] = cellItem
            cellRecyclerViewAdapter?.changeItem(row, cells)
        }
    }

    fun notifyDataSetChanged() {
        cellRecyclerViewAdapter?.notifyCellDataSetChanged()
        columnHeaderRecyclerViewAdapter?.notifyDataSetChanged()
        rowHeaderRecyclerViewAdapter?.notifyDataSetChanged()
    }

    private fun dispatchCellDataSetChangesToListeners(newCellItems: List<List<Any>>) {
        dataSetChangedListeners.let { it.forEach { it.onCellItemsChanged(newCellItems) } }
    }

    private fun dispatchColumnHeaderDataSetChangesToListeners(newColumnHeaderItems: List<Any>) {
        dataSetChangedListeners.let { it.forEach { it.onColumnHeaderItemsChanged(newColumnHeaderItems) } }
    }

    private fun dispatchRowHeaderDataSetChangesToListeners(newRowHeaderItems: List<Any>) {
        dataSetChangedListeners.let { it.forEach { it.onRowHeaderItemsChanged(newRowHeaderItems) } }
    }
}
