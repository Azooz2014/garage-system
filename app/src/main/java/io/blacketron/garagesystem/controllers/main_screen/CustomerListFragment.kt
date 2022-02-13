package io.blacketron.garagesystem.controllers.main_screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.blacketron.garagesystem.R
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

class CustomerListFragment : Fragment(){

    private lateinit var recyclerView: RecyclerView

    private lateinit var addCustomerFab: FloatingActionButton

    private lateinit var editDetailsFragmentLauncher: ActivityResultLauncher<Intent>
    private lateinit var detailsFragmentLauncher: ActivityResultLauncher<Intent>

    private lateinit var dao: GarageDao

    private var customers:List<Customer> = emptyList()

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            adapter = CustomerRecyclerViewAdapter(customers, itemClickListener = {
                detailsFragmentLauncher.launch(
                    Intent(context, CustomerDetailsActivity::class.java).apply {
                        putExtra(CUSTOMER_OBJECT, customers[it])
                    }
                )
            })
        }

        //supplies the recycler view with the new observed list.
        dao.getCustomers().observe(viewLifecycleOwner, Observer {
            (recyclerView.adapter as CustomerRecyclerViewAdapter).setData(it)
        })
    }

    private fun initializeCustomers(){
        /**
         * Observing data changes from [GarageDao.getCustomers]*/
        dao.getCustomers().observe(viewLifecycleOwner, Observer {
            customers = it
            Log.i("List Fragment", "customers: $customers")
        })
    }

    private fun setupFragmentLaunchers(){
        editDetailsFragmentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    Toast.makeText(context, getString(R.string.edit_details_toast_success), Toast.LENGTH_SHORT).show()
                    Log.i("List Fragment", "Activity Result is OK!")
                } else {
                    Toast.makeText(context, getString(R.string.edit_details_toast_failure), Toast.LENGTH_SHORT).show()
                    Log.i("List Fragment", "Activity Result is not OK!")
                }
            }

        detailsFragmentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    Toast.makeText(context, "Thank you for paying :)", Toast.LENGTH_SHORT).show()
                    Log.i("List Fragment", "Activity Result is OK!")
                } else {
                    Toast.makeText(context, "Action Cancelled!", Toast.LENGTH_SHORT).show()
                    Log.i("List Fragment", "Activity Result is not OK!")
                }
            }
    }

    /*List testing functions*/
    private fun testCustomerList(): List<Customer>{

        var fakeCustomers: MutableList<Customer> = mutableListOf()

        for(i in 1..10){
            fakeCustomers.add(createFakeCustomer(i))
        }
        return fakeCustomers.toList()
    }

    private fun createFakeCustomer(id: Int): Customer{
        return Customer(
            id = id.toString(),
            firstName = getRandomString(10),
            lastName = getRandomString(10),
            phoneNumber = getRandomString(7),
            carManufacturer = getRandomString(4),
            carModel = getRandomString(4),
            carLicensePlate = getRandomString(4),
            duration = 1
        )
    }

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
    /*End of list testing functions*/

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

    /*private suspend fun createCustomersList(dao: GarageDao): List<Customer> {
        val customers: MutableList<Customer> = mutableListOf()

        val result = CompletableDeferred<List<Customer>>()


        lifecycleScope.launch {
            lifecycleScope.async {
                dao.getCustomers().forEach {
                    customers.add(it)
                }
            }
            result.complete(customers)
        }

        return result.await()
    }*/
}