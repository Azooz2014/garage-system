package io.blacketron.garagesystem.view.edit_details_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout
import io.blacketron.garagesystem.R
import io.blacketron.garagesystem.model.Customer

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_customer_edit_details, container, false)

        firstNameTF = root.findViewById(R.id.firstNameTextField)
        lastNameTF = root.findViewById(R.id.lastNameTextField)
        phoneNumberTF = root.findViewById(R.id.phoneNumberTextField)
        carMfTF = root.findViewById(R.id.carMfTextField)
        carModelTF = root.findViewById(R.id.carModelTextField)
        carLicensePltTF = root.findViewById(R.id.carLicensePltTextField)
        parkDurationTF = root.findViewById(R.id.parkDurationTextField)

        submitBtn = root.findViewById(R.id.buttonSubmit)

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
}