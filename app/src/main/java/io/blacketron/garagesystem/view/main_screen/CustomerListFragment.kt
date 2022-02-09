package io.blacketron.garagesystem.view.main_screen

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
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.blacketron.garagesystem.R
import io.blacketron.garagesystem.data.local.GarageDB
import io.blacketron.garagesystem.data.local.GarageDao
import io.blacketron.garagesystem.model.Customer
import io.blacketron.garagesystem.view.edit_details_screen.CustomerEditDetailsActivity
import kotlinx.coroutines.*

/**
 * A fragment representing a list of Customers.
 */
class CustomerListFragment : Fragment() {

    private lateinit var addCustomerFab: FloatingActionButton

    private lateinit var fragmentLauncher: ActivityResultLauncher<Intent>

    private lateinit var db: GarageDB
    private lateinit var dao: GarageDao

    private lateinit var customers:List<Customer>

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        db = GarageDB.getInstance(requireContext())
        dao = db.garageDao()

        fragmentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    Toast.makeText(context, getString(R.string.toast_success), Toast.LENGTH_SHORT).show()
                    Log.i("List Fragment", "Activity Result is OK!")
                } else {
                    Toast.makeText(context, getString(R.string.toast_failure), Toast.LENGTH_SHORT).show()
                    Log.i("List Fragment", "Activity Result is not OK!")
                }
        }

        /**
         * Retrieve data from [GarageDao]*/
        lifecycleScope.launch {
            customers = createCustomersList(dao)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_customer_list_list, container, false)

        addCustomerFab = view.findViewById(R.id.addCustomerFab)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        // Set the recycler view adapter
            with(recyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = CustomerRecyclerViewAdapter(customers)
            }

        addCustomerFab.setOnClickListener(View.OnClickListener {
            fragmentLauncher.launch(
                Intent(context, CustomerEditDetailsActivity::class.java)
            )
        })

        return view
    }

    private suspend fun createCustomersList(dao: GarageDao): List<Customer> {
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
}