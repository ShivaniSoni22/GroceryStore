package com.country.grocerystore.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.country.grocerystore.R
import com.country.tableview.adapter.AbstractTableAdapter
import com.country.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.country.grocerystore.models.RandomDataCell
import com.country.grocerystore.viewholders.RandomDataCellViewHolder
import com.country.grocerystore.viewholders.RandomDataColumnHeaderViewHolder
import com.country.grocerystore.viewholders.RandomDataRowHeaderViewHolder

class RandomDataTableViewAdapter(private val context: Context) : AbstractTableAdapter(context) {

    override fun getColumnHeaderItemViewType(column: Int): Int = 0

    override fun getRowHeaderItemViewType(row: Int): Int = 0

    override fun getCellItemViewType(column: Int): Int = 0

    override fun onCreateCellViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): RecyclerView.ViewHolder {
        val cellView = LayoutInflater.from(context).inflate(
                R.layout.table_cell_text_data,
                parent,
                false
        )

        return RandomDataCellViewHolder(cellView)
    }

    override fun onBindCellViewHolder(
            holder: AbstractViewHolder,
            cellItem: Any,
            column: Int,
            row: Int
    ) {
        val cell = cellItem as RandomDataCell
        val cellViewHolder = holder as RandomDataCellViewHolder
        cellViewHolder.cellTextView.text = cell.content.toString()
    }

    override fun onCreateColumnHeaderViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): RecyclerView.ViewHolder {
        val columnHeaderView = LayoutInflater.from(context).inflate(
                R.layout.table_column_header_text_data,
                parent,
                false
        )

        return RandomDataColumnHeaderViewHolder(columnHeaderView)
    }

    override fun onBindColumnHeaderViewHolder(
            holder: AbstractViewHolder,
            columnHeaderItem: Any,
            column: Int
    ) {
        val columnHeaderCell = columnHeaderItem as RandomDataCell
        val columnHeaderViewHolder = holder as RandomDataColumnHeaderViewHolder
        columnHeaderViewHolder.cellTextView.text = columnHeaderCell.content.toString()
    }

    override fun onCreateRowHeaderViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): RecyclerView.ViewHolder {
        val rowHeaderView = LayoutInflater.from(context).inflate(
                R.layout.table_row_header_text_data,
                parent,
                false
        )

        return RandomDataRowHeaderViewHolder(rowHeaderView)
    }

    override fun onBindRowHeaderViewHolder(
            holder: AbstractViewHolder,
            rowHeaderItem: Any,
            row: Int
    ) {
        val rowHeaderCell = rowHeaderItem as RandomDataCell
        val rowHeaderViewHolder = holder as RandomDataRowHeaderViewHolder
        rowHeaderViewHolder.cellTextView.text = rowHeaderCell.content.toString()
    }

    override fun onCreateCornerView(): View? {
        val cornerView = LayoutInflater.from(context).inflate(R.layout.table_corner_view, null)
        return cornerView
    }
}