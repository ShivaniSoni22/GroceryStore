package com.country.grocerystore.viewholders

import android.view.View
import android.widget.TextView
import com.country.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.country.grocerystore.R

class RandomDataColumnHeaderViewHolder(itemView: View) : AbstractViewHolder(itemView) {
    val cellTextView: TextView
        get() = itemView.findViewById(R.id.column_header_text)
}