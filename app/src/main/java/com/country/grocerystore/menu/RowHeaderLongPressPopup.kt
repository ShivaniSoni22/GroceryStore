package com.country.grocerystore.menu

import android.view.MenuItem
import android.widget.PopupMenu
import com.country.tableview.ITableView
import com.country.grocerystore.viewholders.RandomDataRowHeaderViewHolder

class RowHeaderLongPressPopup(
        viewHolder: RandomDataRowHeaderViewHolder,
        private val tableView: ITableView
) : PopupMenu(
        viewHolder.itemView.context,
        viewHolder.itemView
), PopupMenu.OnMenuItemClickListener {

    private val context = viewHolder.itemView.context

    init {
//        createMenuItems()
        setOnMenuItemClickListener(this)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            SCROLL_COLUMN -> tableView.scrollToColumn(50)
            SHOW_HIDE_COLUMN -> {
                if (tableView.isColumnVisible(SAMPLE_SHOW_HIDE_COLUMN)) {
                    tableView.hideColumn(SAMPLE_SHOW_HIDE_COLUMN)
                } else {
                    tableView.showColumn(SAMPLE_SHOW_HIDE_COLUMN)
                }
            }
        }

        return true
    }

//    private fun createMenuItems() {
//        menu.add(Menu.NONE, SCROLL_COLUMN, 0, context.getString(R.string.scroll_to_column_position))
//        menu.add(Menu.NONE, SHOW_HIDE_COLUMN, 1, context.getString(R.string.show_hide_column))
//    }

    companion object {
        const val SCROLL_COLUMN = 0
        const val SHOW_HIDE_COLUMN = 1
        const val SAMPLE_SHOW_HIDE_COLUMN = 5
    }
}