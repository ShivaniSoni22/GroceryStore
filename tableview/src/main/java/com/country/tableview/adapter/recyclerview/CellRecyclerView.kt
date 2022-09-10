package com.country.tableview.adapter.recyclerview

import android.content.Context
import android.util.Log
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.country.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.country.tableview.adapter.recyclerview.holder.AbstractViewHolder.SelectionState
import com.country.tableview.listener.scroll.HorizontalRecyclerViewListener
import com.country.tableview.listener.scroll.VerticalRecyclerViewListener

class CellRecyclerView(context: Context) : RecyclerView(context) {

    var scrolledX = 0
        private set

    var scrolledY = 0
        private set

    var isHorizontalScrollListenerRemoved = true
        private set

    var isVerticalScrollListenerRemoved = true
        private set

    val isScrollOthers: Boolean
        get() = !isHorizontalScrollListenerRemoved

    init {
        setHasFixedSize(false)
        isNestedScrollingEnabled = false
    }

    override fun onScrolled(dx: Int, dy: Int) {
        scrolledX += dx
        scrolledY += dy
        super.onScrolled(dx, dy)
    }

    override fun addOnScrollListener(listener: RecyclerView.OnScrollListener) {
        when (listener) {
            is HorizontalRecyclerViewListener -> {
                if (isHorizontalScrollListenerRemoved) {
                    isHorizontalScrollListenerRemoved = false
                    super.addOnScrollListener(listener)
                } else {
                    Log.w(LOG_TAG, "HorizontalRecyclerViewListener has tried to add itself " +
                            "before removing the old one.")
                }
            }

            is VerticalRecyclerViewListener -> {
                if (isVerticalScrollListenerRemoved) {
                    isVerticalScrollListenerRemoved = false
                    super.addOnScrollListener(listener)
                } else {
                    Log.w(LOG_TAG, "VerticalRecyclerViewListener has tried to add itself " +
                            "before removing the old one.")
                }
            }

            else -> {
                super.addOnScrollListener(listener)
            }
        }
    }

    override fun removeOnScrollListener(listener: RecyclerView.OnScrollListener) {
        when (listener) {
            is HorizontalRecyclerViewListener -> {
                if (isHorizontalScrollListenerRemoved) {
                    Log.e(LOG_TAG, "HorizontalRecyclerViewListener has tried to remove " +
                            "itself before adding new one.")
                } else {
                    isHorizontalScrollListenerRemoved = true
                    super.removeOnScrollListener(listener)
                }
            }

            is VerticalRecyclerViewListener -> {
                if (isVerticalScrollListenerRemoved) {
                    Log.e(LOG_TAG, "VerticalRecyclerViewListener has tried to remove " +
                            "itself before adding new one.")
                } else {
                    isVerticalScrollListenerRemoved = true
                    super.removeOnScrollListener(listener)
                }
            }

            else -> {
                super.removeOnScrollListener(listener)
            }
        }
    }

    fun setSelected(
        selectionState: SelectionState,
        @ColorInt backgroundColor: Int,
        ignoreSelectionColors: Boolean
    ) {
        (0 until (adapter?.itemCount ?: 0)).forEach { i ->
            val viewHolder = findViewHolderForAdapterPosition(i) as AbstractViewHolder?
            if (!ignoreSelectionColors) {
                viewHolder?.setBackgroundColor(backgroundColor)
            }

            viewHolder?.setSelected(selectionState)
        }
    }

    fun clearScrolledX() {
        scrolledX = 0
    }

    companion object {
        private val LOG_TAG = CellRecyclerView::class.java.simpleName
    }
}