package io.blacketron.garagesystem.view.main_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.blacketron.garagesystem.databinding.FragmentCustomerListBinding
import io.blacketron.garagesystem.model.Customer
import io.blacketron.garagesystem.view.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Refresh the list when new data is add to the database and implement onClick.
 *
 */
class CustomerRecyclerViewAdapter(
    private val values: List<Customer>
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameView.text = item.firstName
        holder.contentView.text = item.carLicensePlate
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCustomerListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameView: TextView = binding.itemName
        val contentView: TextView = binding.itemContent

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}