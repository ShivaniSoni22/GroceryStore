package com.country.tableview.feature.sort

import androidx.recyclerview.widget.DiffUtil

class RowHeaderSortCallback(
        private val oldCellItems: List<Sortable>,
        private val newCellItems: List<Sortable>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldCellItems.size

    override fun getNewListSize(): Int = newCellItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldCellItems.size > oldItemPosition && newCellItems.size > newItemPosition) {
            val oldId = oldCellItems[oldItemPosition].id
            val newId = newCellItems[newItemPosition].id
            return oldId == newId
        }

        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldCellItems.size > oldItemPosition && newCellItems.size > newItemPosition) {
            val oldContent = oldCellItems[oldItemPosition].content
            val newContent = newCellItems[newItemPosition].content
            return oldContent == newContent
        }

        return false
    }
}
