package com.country.grocerystore.data

import com.country.grocerystore.api.model.Field
import com.country.grocerystore.api.model.Records
import com.country.grocerystore.models.RandomDataCell

class RandomDataFactory(headerList: MutableList<Field>, recordList: MutableList<Records>) {

    var randomCellsList = mutableListOf<Any>()
        private set

    var randomColumnHeadersList = mutableListOf<Any>()
        private set

    var randomRowHeadersList = mutableListOf<Any>()
        private set

    private val numRows = recordList.size
    private val numColumns = headerList.size

    init {
        (0 until recordList.size).forEach {
            randomRowHeadersList.add(RandomDataCell("${it+1}"))
        }

        headerList.forEach {
            randomColumnHeadersList.add(RandomDataCell("${it.name}"))
        }

        (0 until numRows).forEach { row ->
            val cellList = mutableListOf<Any>()
            (0 until numColumns).forEach { column ->
                val data = when (column) {
                    0 -> recordList[row].state ?: ""
                    1 -> recordList[row].district ?: ""
                    2 -> recordList[row].market ?: ""
                    3 -> recordList[row].commodity ?: ""
                    4 -> recordList[row].variety ?: ""
                    5 -> recordList[row].arrivalDate ?: ""
                    6 -> recordList[row].minPrice ?: ""
                    7 -> recordList[row].maxPrice ?: ""
                    8 -> recordList[row].modalPrice ?: ""
                    else -> {
                        ""
                    }
                } as Any
                cellList.add(RandomDataCell(data, """$column-$row"""))
            }
            randomCellsList.add(cellList)
        }
    }
}