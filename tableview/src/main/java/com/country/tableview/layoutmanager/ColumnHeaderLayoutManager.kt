package com.country.tableview.layoutmanager

import android.content.Context
import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.country.tableview.ITableView
import com.country.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.country.tableview.util.TableViewUtils

class ColumnHeaderLayoutManager(
        context: Context,
        private val tableView: ITableView
) : LinearLayoutManager(context) {

    private val cachedWidthList: SparseArray<Int> = SparseArray()

    val firstItemLeft: Int
        get() {
            val firstColumnHeader = findViewByPosition(findFirstVisibleItemPosition())
            return firstColumnHeader?.left ?: 0
        }

    val visibleViewHolders: Array<AbstractViewHolder?>
        get() {
            val visibleChildCount = findLastVisibleItemPosition() - findFirstVisibleItemPosition() + 1
            val views = arrayOfNulls<AbstractViewHolder>(visibleChildCount)
            for ((index, i) in (findFirstVisibleItemPosition()
                    until findLastVisibleItemPosition() + 1).withIndex()) {
                views[index] = tableView.columnHeaderRecyclerView
                        .findViewHolderForAdapterPosition(i) as AbstractViewHolder

            }

            return views
        }

    init {
        orientation = HORIZONTAL
    }

    override fun measureChildWithMargins(child: View, widthUsed: Int, heightUsed: Int) {
        super.measureChildWithMargins(child, widthUsed, heightUsed)
        if (tableView.hasFixedWidth) {
            return
        }

        measureChild(child, widthUsed, heightUsed)
    }

    override fun measureChild(child: View, widthUsed: Int, heightUsed: Int) {
        if (tableView.hasFixedWidth) {
            super.measureChild(child, widthUsed, heightUsed)
            return
        }

        val position = getPosition(child)
        val cacheWidth = getCacheWidth(position)

        if (cacheWidth != -1) {
            TableViewUtils.setWidth(child, cacheWidth)
        } else {
            super.measureChild(child, widthUsed, heightUsed)
        }
    }


    fun setCacheWidth(position: Int, width: Int) {
        cachedWidthList.put(position, width)
    }

    fun getCacheWidth(position: Int): Int {
        val cachedWidth = cachedWidthList.get(position)
        return if (cachedWidth != null) {
            cachedWidthList.get(position)
        } else -1
    }

    fun removeCachedWidth(position: Int) {
        cachedWidthList.remove(position)
    }

    fun customRequestLayout() {
        var left = firstItemLeft
        var right: Int
        for (i in findFirstVisibleItemPosition() until findLastVisibleItemPosition() + 1) {
            right = left + getCacheWidth(i)
            val columnHeader = findViewByPosition(i)
            if (columnHeader != null) {
                columnHeader.left = left
                columnHeader.right = right
                layoutDecoratedWithMargins(
                    columnHeader,
                    columnHeader.left,
                    columnHeader.top,
                    columnHeader.right,
                    columnHeader.bottom
                )
            }
            left = right + 1
        }
    }

    fun getViewHolder(xPosition: Int): AbstractViewHolder {
        return tableView.columnHeaderRecyclerView
                .findViewHolderForAdapterPosition(xPosition) as AbstractViewHolder
    }
}
