package io.blacketron.garagesystem.utils

import androidx.recyclerview.widget.DiffUtil
import io.blacketron.garagesystem.model.Customer

/**
 * A utility class implementing the [DiffUtil.Callback]
 * to calculates the difference between two lists and outputs a list of update operations
 * that converts the first list into the second one.
 * */

class ListDiffUtil(
    private val oldList: List<Customer>,
    private val newList: List<Customer>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id ->
                false
            oldList[oldItemPosition].firstName != newList[newItemPosition].firstName ->
                false
            oldList[oldItemPosition].lastName != newList[newItemPosition].lastName ->
                false
            oldList[oldItemPosition].phoneNumber != newList[newItemPosition].phoneNumber ->
                false
            oldList[oldItemPosition].carManufacturer != newList[newItemPosition].carManufacturer ->
                false
            oldList[oldItemPosition].carModel != newList[newItemPosition].carModel ->
                false
            oldList[oldItemPosition].carLicensePlate != newList[newItemPosition].carLicensePlate ->
                false
            oldList[oldItemPosition].duration != newList[newItemPosition].duration ->
                false
            else -> true
        }
    }
}