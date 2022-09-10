package com.country.grocerystore.viewholders

import android.view.View
import android.widget.TextView
import com.country.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.country.grocerystore.R

class RandomDataCellViewHolder(itemView: View) : AbstractViewHolder(itemView) {
    val cellTextView: TextView
        get() = itemView.findViewById(R.id.random_data_cell_data)
}