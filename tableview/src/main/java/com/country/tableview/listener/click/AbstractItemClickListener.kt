package com.country.tableview.listener.click

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.country.tableview.ITableView
import com.country.tableview.adapter.recyclerview.CellRecyclerView

abstract class AbstractItemClickListener(
        protected val recyclerView: CellRecyclerView,
        protected val tableView: ITableView
) : RecyclerView.OnItemTouchListener {

    protected var tableViewListener = tableView.tableViewListener

    protected var selectionHandler = tableView.selectionHandler

    protected var gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(
                recyclerView.context,
                object : GestureDetector.SimpleOnGestureListener() {

                    internal var start: MotionEvent? = null

                    override fun onSingleTapUp(e: MotionEvent): Boolean = true

                    override fun onDown(e: MotionEvent): Boolean {
                        start = e
                        return false
                    }

                    override fun onLongPress(e: MotionEvent) {
                        if (
                                start != null &&
                                Math.abs(start!!.rawX - e.rawX) < 20 &&
                                Math.abs(start!!.rawY - e.rawY) < 20
                        ) {
                            longPressAction(e)
                        }
                    }
                })
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean =
            clickAction(view, e)

    override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    protected abstract fun clickAction(view: RecyclerView, e: MotionEvent): Boolean

    protected abstract fun longPressAction(e: MotionEvent)
}
