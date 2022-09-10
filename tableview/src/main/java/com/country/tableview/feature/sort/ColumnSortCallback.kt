package com.country.tableview.feature.sort

import androidx.recyclerview.widget.DiffUtil

class ColumnSortCallback(
        private val oldCellItems: List<List<Sortable>>,
        private val newCellItems: List<List<Sortable>>,
        private val columnPosition: Int
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldCellItems.size

    override fun getNewListSize(): Int = newCellItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldCellItems.size > oldItemPosition && newCellItems.size > newItemPosition) {
            if (
                    oldCellItems[oldItemPosition].size > columnPosition &&
                    newCellItems[newItemPosition].size > columnPosition
            ) {
                val oldId = oldCellItems[oldItemPosition][columnPosition].id
                val newId = newCellItems[newItemPosition][columnPosition].id
                return oldId == newId
            }
        }

        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldCellItems.size > oldItemPosition && newCellItems.size > newItemPosition) {
            if (
                    oldCellItems[oldItemPosition].size > columnPosition &&
                    newCellItems[newItemPosition].size > columnPosition
            ) {
                val oldContent = oldCellItems[oldItemPosition][columnPosition].content
                val newContent = newCellItems[newItemPosition][columnPosition].content
                return oldContent == newContent
            }
        }

        return false
    }
}
