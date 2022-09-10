package com.country.grocerystore.models

import com.country.tableview.feature.filter.Filterable
import com.country.tableview.feature.sort.Sortable

class RandomDataCell(
        _data: Any,
        _id: String = _data.hashCode().toString(),
        _filter: String = _data.toString()
) : Filterable, Sortable {

    override var filterableKeyword: String = _filter

    override var id: String = _id

    override var content: Any = _data
}