package io.blacketron.garagesystem.controllers.main_screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.blacketron.garagesystem.R
import io.blacketron.garagesystem.controllers.about_screen.AboutActivity
import io.blacketron.garagesystem.controllers.about_screen.AboutFragment
import io.blacketron.garagesystem.data.local.GarageDB
import io.blacketron.garagesystem.data.local.GarageDao
import io.blacketron.garagesystem.model.Customer
import io.blacketron.garagesystem.controllers.details_screen.CustomerDetailsActivity
import io.blacketron.garagesystem.controllers.edit_details_screen.CustomerEditDetailsActivity

/**
 * A fragment representing a list of customers.
 * It's the main screen of the app.
 */

const val CUSTOMER_OBJECT = "customerObject"

class CustomerListFragment : Fragment(), SearchView.OnQueryTextListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var mainAdapter: CustomerRecyclerViewAdapter

    private lateinit var addCustomerFab: FloatingActionButton

    private lateinit var editDetailsFragmentLauncher: ActivityResultLauncher<Intent>
    private lateinit var detailsFragmentLauncher: ActivityResultLauncher<Intent>
    private lateinit var aboutFragmentLauncher: ActivityResultLauncher<Intent>

    private lateinit var dao: GarageDao

    private var customers:List<Customer> = emptyList()

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        dao = GarageDB.getInstance(requireContext()).garageDao()

        setupFragmentLaunchers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_customer_list_list, container, false)

        addCustomerFab = view.findViewById(R.id.addCustomerFab)

        recyclerView = view.findViewById(R.id.recyclerView)

        /**
         * initialize [customers] list with data observed from [GarageDao.getCustomers]*/
        initializeCustomers()

        initializeRecyclerViewAdapter()

        addCustomerFab.setOnClickListener(View.OnClickListener {
            editDetailsFragmentLauncher.launch(
                Intent(context, CustomerEditDetailsActivity::class.java)
            )
        })

        return view
    }

    private fun initializeRecyclerViewAdapter(){
        // Set the recycler view adapter
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = CustomerRecyclerViewAdapter(customers, itemClickListener = { index ->
                detailsFragmentLauncher.launch(
                    Intent(context, CustomerDetailsActivity::class.java).apply {
                        putExtra(CUSTOMER_OBJECT, customers[index])
                    }
                )
            })
        }

        //supplies the recycler view with the new observed list.
        mainAdapter = recyclerView.adapter as CustomerRecyclerViewAdapter
        dao.getCustomers().observe(viewLifecycleOwner, Observer { list ->
            mainAdapter.setData(list)
        })
    }

    private fun initializeCustomers(){
        /**
         * Observing data changes from [GarageDao.getCustomers]*/
        dao.getCustomers().observe(viewLifecycleOwner, Observer { list ->
            customers = list
        })
    }

    private fun setupFragmentLaunchers(){
        editDetailsFragmentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    Toast.makeText(context, getString(R.string.edit_details_toast_success), Toast.LENGTH_SHORT).show()
                    Log.i("List Fragment", "Activity Result is OK!")
                } else {
                    Toast.makeText(context, getString(R.string.edit_details_toast_failure), Toast.LENGTH_SHORT).show()
                    Log.i("List Fragment", "Activity Result is not OK!")
                }
            }

        detailsFragmentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    Toast.makeText(context, "Thank you for paying :)", Toast.LENGTH_SHORT).show()
                    Log.i("List Fragment", "Activity Result is OK!")
                } else {
                    Toast.makeText(context, "Action Cancelled!", Toast.LENGTH_SHORT).show()
                    Log.i("List Fragment", "Activity Result is not OK!")
                }
            }

        aboutFragmentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    Log.i("List Fragment", "Activity Result is OK!")
                } else {
                    Log.i("List Fragment", "Activity Result is not OK!")
                }
            }

        aboutFragmentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    Log.i("List Fragment", "Activity Result is OK!")
                } else {
                    Log.i("List Fragment", "Activity Result is not OK!")
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)

        val searchMenuItem = menu.findItem(R.id.menu_search)
        val searchView = searchMenuItem.actionView as SearchView

        searchView.isSubmitButtonEnabled = true

        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.menu_about -> {
                //Launch About Fragment.
                aboutFragmentLauncher.launch(Intent(context, AboutActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String?){
        val searchQuery = "%$query%"

        dao.getCustomerByLicense(searchQuery).observe(viewLifecycleOwner, Observer { list ->
            mainAdapter.setData(list)
            customers = list
        })
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            CustomerListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}