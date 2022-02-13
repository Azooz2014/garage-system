package io.blacketron.garagesystem.controllers.main_screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.blacketron.garagesystem.R

/**
* Entry point of the App and the host of [CustomerListFragment]
*/

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}