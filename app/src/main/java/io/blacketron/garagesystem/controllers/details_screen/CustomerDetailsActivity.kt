package io.blacketron.garagesystem.controllers.details_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import io.blacketron.garagesystem.R

class CustomerDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_details)
    }

    //Action Bar Up Button option.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // setResult and close the activity when Action Bar Up Button clicked.
        if (item.itemId == android.R.id.home) {
            setResult(RESULT_CANCELED)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}