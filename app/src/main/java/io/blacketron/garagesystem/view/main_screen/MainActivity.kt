package io.blacketron.garagesystem.view.main_screen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.blacketron.garagesystem.R
import io.blacketron.garagesystem.data.local.GarageDB
import io.blacketron.garagesystem.data.local.GarageDao
import io.blacketron.garagesystem.model.Customer
import io.blacketron.garagesystem.view.edit_details_screen.CustomerEditDetailsActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addCustomerFab: FloatingActionButton = findViewById(R.id.addCustomerFab)
        val db = GarageDB.getInstance(this)
        val dao = db.garageDao()

        val fragmentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                Log.i("Main Activity", "details fragment launched!")
            }
        }

        /*//For testing
        GlobalScope.launch {
            dao.insertCustomer(testingCustomer())
        }*/

        //TODO: Move the FAB to the fragment.

        addCustomerFab.setOnClickListener(View.OnClickListener {

            fragmentLauncher.launch(
                Intent(this, CustomerEditDetailsActivity::class.java)
            )
        })
    }

    fun testingCustomer(): Customer{
        return Customer(firstName = "test",
            lastName = "testing",
            phoneNumber = "000",
            carManufacturer = "test",
            carModel = "test",
            carLicensePlate = "abc123",
            duration = 1
        )
    }
}