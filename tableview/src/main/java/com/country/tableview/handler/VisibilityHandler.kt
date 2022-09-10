package com.country.tableview.handler

import android.util.Log
import android.util.SparseArray
import com.country.tableview.ITableView

class VisibilityHandler(private val tableView: ITableView) {

    private val hiddenRowsList = SparseArray<Row>()

    private val hiddenColumnsList = SparseArray<Column>()

    fun hideRow(row: Int) {
        hiddenRowsList.put(row, getRowValueFromPosition(row))
        tableView.adapter?.removeRow(row)
    }

    fun showRow(row: Int) {
        showRow(row, true)
    }

    private fun showRow(row: Int, removeFromList: Boolean) {
        val hiddenRow = hiddenRowsList.get(row)
        if (hiddenRow != null) {
            tableView.adapter?.addRow(
                    row,
                    hiddenRow.rowHeaderModel,
                    hiddenRow.cellModelList
            )
        } else {
            Log.e(LOG_TAG, "This row is already visible.")
        }

        if (removeFromList) {
            hiddenRowsList.remove(row)
        }
    }

    fun clearHiddenRowList() {
        hiddenRowsList.clear()
    }

    fun showAllHiddenRows() {
        (0 until hiddenRowsList.size())
                .map { hiddenRowsList.keyAt(it) }
                .forEach { showRow(it, false) }

        clearHiddenRowList()
    }

    fun isRowVisible(row: Int): Boolean {
        return hiddenRowsList.get(row) == null
    }

    fun hideColumn(column: Int) {
        hiddenColumnsList.put(column, getColumnValueFromPosition(column))
        tableView.adapter?.removeColumn(column)
    }

    fun showColumn(column: Int) {
        showColumn(column, true)
    }

    private fun showColumn(column: Int, removeFromList: Boolean) {
        val hiddenColumn = hiddenColumnsList.get(column)
        if (hiddenColumn != null) {
            tableView.adapter?.addColumn(
                    column,
                    hiddenColumn.columnHeaderModel,
                    hiddenColumn.cellModelList
            )
        } else {
            Log.e(LOG_TAG, "This column is already visible.")
        }

        if (removeFromList) {
            hiddenColumnsList.remove(column)
        }
    }

    fun clearHiddenColumnsList() {
        hiddenColumnsList.clear()
    }

    fun showAllHiddenColumns() {
        (0 until hiddenColumnsList.size())
                .map { hiddenColumnsList.keyAt(it) }
                .forEach { showColumn(it, false) }

        clearHiddenColumnsList()
    }

    fun isColumnVisible(column: Int): Boolean {
        return hiddenColumnsList.get(column) == null
    }

    private fun getRowValueFromPosition(row: Int): Row {
        val rowHeaderModel = tableView.adapter?.getRowHeaderItem(row)
        val cellModelList = tableView.adapter?.getRowCellItems(row) as List<Any?>
        return Row(row, rowHeaderModel, cellModelList)
    }

    private fun getColumnValueFromPosition(column: Int): Column {
        val columnHeaderModel = tableView.adapter?.getColumnHeaderItem(column)
        val cellModelList = tableView.adapter?.getColumnCellItems(column) as List<Any?>

        return Column(column, columnHeaderModel, cellModelList)
    }

    internal inner class Row(
            val yPosition: Int,
            val rowHeaderModel: Any?,
            val cellModelList: List<Any?>
    )

    internal inner class Column(
            val yPosition: Int,
            val columnHeaderModel: Any?,
            val cellModelList: List<Any?>
    )

    companion object {
        private val LOG_TAG = VisibilityHandler::class.java.simpleName
    }
}
