package com.country.tableview.feature.filter

data class FilterItem(
        val filterType: FilterType,
        val column: Int,
        val filter: String
)