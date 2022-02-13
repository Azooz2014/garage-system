package io.blacketron.garagesystem.controllers.edit_details_screen

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import io.blacketron.garagesystem.R
import io.blacketron.garagesystem.data.local.GarageDB
import io.blacketron.garagesystem.data.local.GarageDao
import io.blacketron.garagesystem.model.Customer
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CustomerEditDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CustomerEditDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var firstNameTF: TextInputLayout
    private lateinit var lastNameTF: TextInputLayout
    private lateinit var phoneNumberTF: TextInputLayout
    private lateinit var carMfTF: TextInputLayout
    private lateinit var carModelTF: TextInputLayout
    private lateinit var carLicensePltTF: TextInputLayout
    private lateinit var parkDurationTF: TextInputLayout

    private lateinit var submitBtn: Button

    private lateinit var dao: GarageDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        dao = GarageDB.getInstance(requireContext()).garageDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_customer_edit_details, container, false)

        setupUI(root)

        submitBtn.setOnClickListener(View.OnClickListener {
            if(isInputValid()){
                saveToDB(dao)

                activity?.setResult(Activity.RESULT_OK)
                activity?.finish()
            }
        })

        // Inflate the layout for this fragment
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CustomerEditDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CustomerEditDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setupUI(root: View){
        firstNameTF = root.findViewById(R.id.firstNameTextField)
        lastNameTF = root.findViewById(R.id.lastNameTextField)
        phoneNumberTF = root.findViewById(R.id.phoneNumberTextField)
        carMfTF = root.findViewById(R.id.carMfTextField)
        carModelTF = root.findViewById(R.id.carModelTextField)
        carLicensePltTF = root.findViewById(R.id.carLicensePltTextField)
        parkDurationTF = root.findViewById(R.id.parkDurationTextField)

        submitBtn = root.findViewById(R.id.buttonSubmit)
    }

    private fun saveToDB(dao: GarageDao){
            //create new customer & save the text field values into the customer.
            val customer = Customer(
                firstName = firstNameTF.editText?.text.toString(),
                lastName = lastNameTF.editText?.text.toString(),
                phoneNumber = phoneNumberTF.editText?.text.toString(),
                carManufacturer = carMfTF.editText?.text.toString(),
                carModel = carModelTF.editText?.text.toString(),
                carLicensePlate = carLicensePltTF.editText?.text.toString(),
                duration = parkDurationTF.editText?.text.toString().toInt()
            )

            //insert the customer to db
            lifecycleScope.launch {
                dao.insertCustomer(customer)
            }

    }

    private fun isInputValid(): Boolean{
        if (
            isFirstNameValid() and
            isLastNameValid() and
            isPhoneNumberValid() and
            isCarMftValid() and
            isCarModelValid() and
            isCarLicenseValid() and
            isParkDurationValid()
        ) return true

        return false
    }

    /*TextInput validation functions*/
    private fun isFirstNameValid(): Boolean{
        if (firstNameTF.editText!!.text.isEmpty()) {
            showError(firstNameTF, getString(R.string.tf_error_msg))
            return false
        }
        firstNameTF.isErrorEnabled = false
        return true
    }

    private fun isLastNameValid(): Boolean{
        if (lastNameTF.editText!!.text.isEmpty()) {
            showError(lastNameTF, getString(R.string.tf_error_msg))
            return false
        }
        lastNameTF.isErrorEnabled = false
        return true
    }

    private fun isPhoneNumberValid(): Boolean{
        if (phoneNumberTF.editText!!.text.isEmpty()) {
            showError(phoneNumberTF, getString(R.string.tf_error_msg))
            return false
        }
        phoneNumberTF.isErrorEnabled = false
        return true
    }

    private fun isCarMftValid(): Boolean{
        if (carMfTF.editText!!.text.isEmpty()) {
            showError(carMfTF, getString(R.string.tf_error_msg))
            return false
        }
        carMfTF.isErrorEnabled = false
        return true
    }

    private fun isCarModelValid(): Boolean{
        if (carModelTF.editText!!.text.isEmpty()) {
            showError(carModelTF, getString(R.string.tf_error_msg))
            return false
        }
        carModelTF.isErrorEnabled = false
        return true
    }

    private fun isCarLicenseValid(): Boolean{
        if (carLicensePltTF.editText!!.text.isEmpty()) {
            showError(carLicensePltTF, getString(R.string.tf_error_msg))
            return false
        }
        carLicensePltTF.isErrorEnabled = false
        return true
    }

    private fun isParkDurationValid(): Boolean{
        if (parkDurationTF.editText!!.text.isEmpty()) {
            showError(parkDurationTF, getString(R.string.tf_error_msg))
            return false
        }
        parkDurationTF.isErrorEnabled = false
        return true
    }

    /*Showing TextInput error function*/
    private fun showError(view: TextInputLayout, errorMsg: String){
        view.isErrorEnabled = true
        view.error = errorMsg
    }
}