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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CustomerDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CustomerDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

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
        feesValue.text = calculatedFees(price).toString()
    }

    private fun calculatedFees(price: Float): Float{
        return price * (customer?.duration ?: 0)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CustomerDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) {
            CustomerDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        }

    }
}