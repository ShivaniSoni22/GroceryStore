package com.country.tableview.listener.scroll

import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.country.tableview.ITableView
import com.country.tableview.adapter.recyclerview.CellRecyclerView

class HorizontalRecyclerViewListener(
    tableView: ITableView
) : RecyclerView.OnScrollListener(), RecyclerView.OnItemTouchListener {

    private val verticalRecyclerViewListener = tableView.verticalRecyclerViewListener

    private val columnHeaderRecyclerView = tableView.columnHeaderRecyclerView

    private val cellLayoutManager = tableView.cellRecyclerView.layoutManager

    private var lastTouchedRecyclerView: RecyclerView? = null

    private var isMoved: Boolean = false

    private var xPosition: Int = 0

    var scrollPositionOffset = 0

    var scrollPosition: Int = 0

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        if (e.action == MotionEvent.ACTION_DOWN) {
            if (rv.scrollState == SCROLL_STATE_IDLE) {
                if (lastTouchedRecyclerView != null && rv != lastTouchedRecyclerView) {
                    if (lastTouchedRecyclerView == columnHeaderRecyclerView) {
                        columnHeaderRecyclerView.removeOnScrollListener(this)
                        columnHeaderRecyclerView.stopScroll()
                        Log.d(
                            LOG_TAG,
                            "Scroll listener has been moved to " +
                                    "columnHeaderRecyclerView at last touch control."
                        )
                    } else {
                        val lastTouchedIndex = getIndex(lastTouchedRecyclerView!!)
                        if (
                            lastTouchedIndex >= 0 &&
                            lastTouchedIndex < (cellLayoutManager?.childCount ?: 0)
                        ) {
                            if (!(lastTouchedRecyclerView as CellRecyclerView)
                                    .isHorizontalScrollListenerRemoved
                            ) {
                                (cellLayoutManager?.getChildAt(lastTouchedIndex) as RecyclerView)
                                    .removeOnScrollListener(this)
                                Log.d(
                                    LOG_TAG,
                                    "Scroll listener has been moved to " +
                                            "${lastTouchedRecyclerView!!.id} " +
                                            "CellRecyclerView at last touch control."
                                )

                                (cellLayoutManager.getChildAt(lastTouchedIndex) as RecyclerView)
                                    .stopScroll()
                            }
                        }
                    }
                }

                xPosition = (rv as CellRecyclerView).scrolledX
                rv.addOnScrollListener(this)
                Log.d(
                    LOG_TAG,
                    "Scroll listener has been added to ${rv.id} at action down."
                )
            }
        } else if (e.action == MotionEvent.ACTION_MOVE) {
            isMoved = true
        } else if (e.action == MotionEvent.ACTION_UP) {
            val nScrollX = (rv as CellRecyclerView).scrolledX
            if (xPosition == nScrollX && !isMoved) {
                rv.removeOnScrollListener(this)
                Log.d(
                    LOG_TAG,
                    "Scroll listener  has been removed to ${rv.id} at action up."
                )
            }

            lastTouchedRecyclerView = rv
        } else if (e.action == MotionEvent.ACTION_CANCEL) {
            renewScrollPosition(rv)
            rv.removeOnScrollListener(this)
            Log.d(
                LOG_TAG,
                "Scroll listener  has been removed to ${rv.id} at action cancel."
            )

            lastTouchedRecyclerView = rv
            isMoved = false
        }

        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (recyclerView == columnHeaderRecyclerView) {
            super.onScrolled(recyclerView, dx, dy)
            (0 until (cellLayoutManager?.childCount ?: 0))
                .map { cellLayoutManager?.getChildAt(it) as CellRecyclerView }
                .forEach { it.scrollBy(dx, 0) }
        } else {
            recyclerView.let { super.onScrolled(it, dx, dy) }
            (0 until (cellLayoutManager?.childCount ?: 0))
                .map { cellLayoutManager?.getChildAt(it) as CellRecyclerView }
                .filter { it !== recyclerView }
                .forEach { it.scrollBy(dx, 0) }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == SCROLL_STATE_IDLE) {
            renewScrollPosition(recyclerView)
            recyclerView.removeOnScrollListener(this)
            Log.d(
                LOG_TAG,
                "Scroll listener has been moved to " +
                        "${recyclerView.id} at onScrollStateChanged."
            )

            isMoved = false
            val isNeeded = lastTouchedRecyclerView !== columnHeaderRecyclerView
            verticalRecyclerViewListener.removeLastTouchedRecyclerViewScrollListener(isNeeded)
        }
    }

    private fun getIndex(rv: RecyclerView): Int {
        (0 until (cellLayoutManager?.childCount ?: 0)).forEach { i ->
            val child = cellLayoutManager?.getChildAt(i) as RecyclerView
            if (child == rv) {
                return i
            }
        }

        return -1
    }

    private fun renewScrollPosition(recyclerView: RecyclerView?) {
        val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
        scrollPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (scrollPosition == -1) {
            scrollPosition = layoutManager.findFirstVisibleItemPosition()
            if (layoutManager.findFirstVisibleItemPosition() ==
                layoutManager.findLastVisibleItemPosition()
            ) {
            } else {
                scrollPosition += 1
            }
        }

        scrollPositionOffset =
            recyclerView.layoutManager?.findViewByPosition(scrollPosition)?.left ?: -1
    }

    companion object {
        private val LOG_TAG = HorizontalRecyclerViewListener::class.java.simpleName
    }
}
