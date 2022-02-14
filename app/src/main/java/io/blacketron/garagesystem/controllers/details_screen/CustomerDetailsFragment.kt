package io.blacketron.garagesystem.controllers.details_screen

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import io.blacketron.garagesystem.R
import io.blacketron.garagesystem.model.Customer
import io.blacketron.garagesystem.controllers.main_screen.CUSTOMER_OBJECT

/**
 * A simple [Fragment] subclass.
 */
class CustomerDetailsFragment : Fragment() {

    private lateinit var firstNameValue: TextView
    private lateinit var lastNameValue: TextView
    private lateinit var carMfValue: TextView
    private lateinit var carModelValue: TextView
    private lateinit var carLicensePltValue: TextView
    private lateinit var feesValue: TextView

    private lateinit var payBtn: Button

    private var customer: Customer? = null

    private val price: Float = 5f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customer = activity?.intent?.getParcelableExtra(CUSTOMER_OBJECT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_customer_details, container, false)

        setupUI(root)

        payBtn.setOnClickListener {
            activity?.setResult(Activity.RESULT_OK)
            activity?.finish()
        }

        return root
    }

    private fun setupUI(root: View){
        firstNameValue = root.findViewById(R.id.firstNameValue)
        lastNameValue = root.findViewById(R.id.lastNameValue)
        carMfValue = root.findViewById(R.id.carManValue)
        carModelValue = root.findViewById(R.id.carModelValue)
        carLicensePltValue = root.findViewById(R.id.carLicensePltValue)
        feesValue = root.findViewById(R.id.feesValue)
        payBtn = root.findViewById(R.id.payButton)

        firstNameValue.text = customer?.firstName
        lastNameValue.text = customer?.lastName
        carMfValue.text = customer?.carManufacturer
        carModelValue.text = customer?.carModel
        carLicensePltValue.text = customer?.carLicensePlate
        feesValue.text = getString(R.string.text_label_fees, formattedFloat(calculatedFees(price)))
    }

    private fun calculatedFees(price: Float): Float{
        return price * (customer?.duration ?: 0)
    }

    /** Formats the fees value accordingly.
     * e.g if reminder of the division is 0 then there's no need to show the decimal numbers
     * after the point because they're all zeros.
     * else just show 2 places after the decimal*/
    private fun formattedFloat(value: Float): String{
        if(value % 1f == 0f){
            return "%.0f".format(value)
        }
        return "%.2f".format(value)
    }
}