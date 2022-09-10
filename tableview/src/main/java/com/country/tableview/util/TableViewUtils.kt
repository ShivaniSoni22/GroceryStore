package com.country.tableview.util

import android.view.View
import android.view.View.MeasureSpec.EXACTLY
import android.view.View.MeasureSpec.makeMeasureSpec
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

object TableViewUtils {

    fun setWidth(view: View, width: Int) {
        val params = view.layoutParams as RecyclerView.LayoutParams
        params.width = width
        view.layoutParams = params
        val widthMeasureSpec = makeMeasureSpec(width, EXACTLY)
        val heightMeasureSpec = makeMeasureSpec(
                view.measuredHeight,
                EXACTLY
        )

        view.measure(widthMeasureSpec, heightMeasureSpec)
        view.requestLayout()
    }

    fun getWidth(view: View): Int {
        view.measure(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                makeMeasureSpec(
                        view.measuredHeight,
                        EXACTLY
                )
        )

        return view.measuredWidth
    }
}
