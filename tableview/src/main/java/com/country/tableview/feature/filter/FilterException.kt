package com.country.tableview.feature.filter

class FilterException : Exception {

    constructor() : super("Error in searching from table.")

    constructor(column: String, query: String) : super(
            "Error searching " + query + " in column " + column + " of table. Column contents "
                    + "are not of class " + String::class.java.canonicalName)
}