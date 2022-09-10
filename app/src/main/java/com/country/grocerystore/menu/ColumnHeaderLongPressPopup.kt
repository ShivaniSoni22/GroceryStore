package com.country.grocerystore.menu

import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import com.country.tableview.TableView
import com.country.tableview.feature.sort.SortState
import com.country.grocerystore.R
import com.country.grocerystore.viewholders.RandomDataColumnHeaderViewHolder

class ColumnHeaderLongPressPopup(
    viewHolder: RandomDataColumnHeaderViewHolder,
    private val tableView: TableView
) : PopupMenu(
    viewHolder.itemView.context,
    viewHolder.itemView
), PopupMenu.OnMenuItemClickListener {

    private val context = viewHolder.itemView.context

    private val xPosition = viewHolder.adapterPosition

    init {
        createMenuItems()
        changeMenuItemsVisibility()
        setOnMenuItemClickListener(this)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            ASCENDING -> tableView.sortColumn(xPosition, SortState.ASCENDING)
            DESCENDING -> tableView.sortColumn(xPosition, SortState.DESCENDING)
            HIDE_ROW -> tableView.hideRow(3)
            SHOW_ROW -> tableView.showRow(3)
            SCROLL_ROW -> tableView.scrollToRow(100)
        }

        return true
    }

    private fun createMenuItems() {
        menu.add(Menu.NONE, ASCENDING, 0, context.getString(R.string.sort_ascending))
        menu.add(Menu.NONE, DESCENDING, 1, context.getString(R.string.sort_descending))
//        menu.add(Menu.NONE, HIDE_ROW, 2, context.getString(R.string.hide_row))
//        menu.add(Menu.NONE, SHOW_ROW, 3, context.getString(R.string.show_row))
//        menu.add(Menu.NONE, SCROLL_ROW, 4, context.getString(R.string.scroll_to_row_position))
    }

    private fun changeMenuItemsVisibility() {
        val sortState = tableView.getColumnSortState(xPosition)
        when (sortState) {
            SortState.DESCENDING -> menu.getItem(1).isVisible = false
            SortState.ASCENDING -> menu.getItem(0).isVisible = false
            SortState.UNSORTED -> {
            }
        }
    }

    companion object {
        const val ASCENDING = 0
        const val DESCENDING = 1
        const val HIDE_ROW = 2
        const val SHOW_ROW = 3
        const val SCROLL_ROW = 4
    }
}