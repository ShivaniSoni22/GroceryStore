package com.country.tableview.feature.sort

class TableViewSortException :
        Exception(
                "For sorting process, column and row header view holders must be " +
                        "extended from " +
                        "AbstractSorterViewHolder class"
        )