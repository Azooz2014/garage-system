package io.blacketron.garagesystem.controllers.main_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.blacketron.garagesystem.R
import io.blacketron.garagesystem.databinding.FragmentCustomerListBinding
import io.blacketron.garagesystem.model.Customer
import io.blacketron.garagesystem.utils.ListDiffUtil

/**
 * [RecyclerView.Adapter] that can display a [Customer] item.
 */
class CustomerRecyclerViewAdapter(
    private var initialList: List<Customer>,
    private val itemClickListener: (position: Int) -> Unit
) : RecyclerView.Adapter<CustomerRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCustomerListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    /*
    * Updates the recycler view list when items are
    * inserted into the database. & refresh the list*/
    fun setData(updatedList: List<Customer>){
        val diffResult = DiffUtil.calculateDiff(ListDiffUtil(initialList, updatedList))
        initialList = updatedList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = initialList[position]
        holder.nameView.text = holder.itemView.context
            .getString(R.string.text_first_name, item.firstName)

        holder.contentView.text = holder.itemView.context
            .getString(R.string.text_license_plate, item.carLicensePlate)
    }

    override fun getItemCount(): Int = initialList.size

    inner class ViewHolder(binding: FragmentCustomerListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                itemClickListener(bindingAdapterPosition)
            }
        }

        val nameView: TextView = binding.itemName
        val contentView: TextView = binding.itemContent

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}