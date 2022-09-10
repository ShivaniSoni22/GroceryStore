package com.country.grocerystore.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.country.grocerystore.R
import com.country.grocerystore.adapters.RandomDataTableViewAdapter
import com.country.grocerystore.api.GroceryService
import com.country.grocerystore.api.RetrofitBuilder
import com.country.grocerystore.api.model.Field
import com.country.grocerystore.api.model.Records
import com.country.grocerystore.data.RandomDataFactory
import com.country.grocerystore.listeners.TableViewListener
import com.country.tableview.TableView
import com.country.tableview.feature.filter.Filter
import com.country.tableview.feature.pagination.Pagination
import kotlinx.coroutines.*

class RandomDataTableViewWithControlsFragment : Fragment() {

    private lateinit var tableView: TableView
    private lateinit var pageNumberField: EditText
    private lateinit var searchField: EditText
    private lateinit var itemsPerPage: Spinner
    private lateinit var previousButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var tablePaginationDetails: TextView
    private lateinit var pagination: Pagination
    private lateinit var filter: Filter
    private var mainView: View? = null
    private var mHeaderList:MutableList<Field> = mutableListOf()
    private var mRecordList:MutableList<Records> = mutableListOf()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_random_data_table_with_controls, container, false)
        initializeViews()
        val service = RetrofitBuilder.getRetrofitWithTimeout(requireContext())
            .create(GroceryService::class.java)
        GlobalScope.launch {
            val response = service.getGroceryData(
                apiKey = "579b464db66ec23bdd0000017650209ada2b4c7a4c4b72c11b681600",
                format = "json",
                limit = "60"
            )
            withContext(Dispatchers.Main) {
                if (response?.body().toString().isNotEmpty() && response?.isSuccessful == true) {
                    mHeaderList.addAll(response.body()?.field?: emptyList())
                    mRecordList.addAll(response.body()?.records?: emptyList())
                }
                initializeData()
                initializeListeners()
            }
        }
        return mainView
    }

    private fun initializeViews() {
        tableView = mainView!!.findViewById(R.id.random_data_tableview)
        itemsPerPage = mainView!!.findViewById(R.id.items_per_page_spinner)
        searchField = mainView!!.findViewById(R.id.query_string)
        previousButton = mainView!!.findViewById(R.id.previous_button)
        nextButton = mainView!!.findViewById(R.id.next_button)
        pageNumberField = mainView!!.findViewById(R.id.page_number_text)
        tablePaginationDetails = mainView!!.findViewById(R.id.table_details)
    }

    private fun initializeListeners() {
        itemsPerPage.onItemSelectedListener = onItemsPerPageSelectedListener
        searchField.addTextChangedListener(onSearchTextChange)
        previousButton.setOnClickListener(onPreviousPageButtonClicked)
        nextButton.setOnClickListener(onNextPageButtonClicked)
        pageNumberField.addTextChangedListener(onPageTextChanged)
        pagination.onTableViewPageTurnedListener = onTableViewPageTurnedListener
    }

    @Suppress("UNCHECKED_CAST")
    private fun initializeData() {
        val randomDataFactory = RandomDataFactory(mHeaderList, mRecordList)
        val tableAdapter = RandomDataTableViewAdapter(mainView!!.context)
        val cellsList = randomDataFactory.randomCellsList as List<List<Any>>
        val rowHeadersList = randomDataFactory.randomRowHeadersList as List<Any>
        val columnHeadersList = randomDataFactory.randomColumnHeadersList as List<Any>
        tableView.adapter = tableAdapter
        tableView.tableViewListener = TableViewListener(tableView)
        tableAdapter.setAllItems(cellsList, columnHeadersList, rowHeadersList)
        pagination = Pagination(tableView)
        filter = Filter(tableView)
    }

    private val onSearchTextChange = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            filter.set(s.toString())
        }
    }

    private val onPageTextChanged = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val page: Int = if (TextUtils.isEmpty(s)) 1 else Integer.valueOf(s.toString())
            pagination.loadPage(page)
        }
    }

    private val onItemsPerPageSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val numItems = when (parent!!.getItemAtPosition(position) as String) {
                "All" -> 0
                else -> Integer.valueOf(parent.getItemAtPosition(position) as String)
            }

            pagination.itemsPerPage = numItems
        }
    }

    private val onPreviousPageButtonClicked = View.OnClickListener {
        pagination.loadPreviousPage()
    }

    private val onNextPageButtonClicked = View.OnClickListener {
        pagination.loadNextPage()
    }

    private val onTableViewPageTurnedListener =
            object : Pagination.OnTableViewPageTurnedListener {
                override fun onPageTurned(numItems: Int, itemsStart: Int, itemsEnd: Int) {
                    val currentPage = pagination.currentPage
                    val pageCount = pagination.pageCount
                    previousButton.visibility = VISIBLE
                    nextButton.visibility = VISIBLE
                    if (currentPage == 1 && pageCount == 1) {
                        previousButton.visibility = INVISIBLE
                        nextButton.visibility = INVISIBLE
                    }
                    if (currentPage == 1) {
                        previousButton.visibility = INVISIBLE
                    }
                    if (currentPage == pageCount) {
                        nextButton.visibility = INVISIBLE
                    }
                    tablePaginationDetails.text = getString(
                            R.string.table_pagination_details,
                            currentPage,
                            itemsStart+1,
                            itemsEnd+1
                    )
                }
            }

    companion object {
        fun newInstance(context: Context): RandomDataTableViewWithControlsFragment {
            val bundle = Bundle()
            val fragment = RandomDataTableViewWithControlsFragment()
            bundle.putString("title", context.resources.getStringArray(R.array.table_view_demo)[0])
            fragment.arguments = bundle
            return fragment
        }
    }
}