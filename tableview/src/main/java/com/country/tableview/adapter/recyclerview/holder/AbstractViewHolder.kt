package com.country.tableview.adapter.recyclerview.holder

import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.country.tableview.adapter.recyclerview.holder.AbstractViewHolder.SelectionState.*

abstract class AbstractViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var selectionState = UNSELECTED

    val isSelected: Boolean
        get() = selectionState == SELECTED

    val isShadowed: Boolean
        get() = selectionState == SHADOWED

    fun setSelected(selectionState: SelectionState) {
        this.selectionState = selectionState
        when (selectionState) {
            SELECTED -> itemView.isSelected = true
            UNSELECTED -> itemView.isSelected = false
            else -> {
                itemView.isSelected = false
            }
        }
    }

    fun setBackgroundColor(@ColorInt color: Int) {
        itemView.setBackgroundColor(color)
    }

    enum class SelectionState {
        SELECTED, UNSELECTED, SHADOWED
    }
}
