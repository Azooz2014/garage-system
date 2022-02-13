package io.blacketron.garagesystem.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.blacketron.garagesystem.model.Customer
import java.util.*

@Dao
interface GarageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer)

    @Query("SELECT * FROM CustomerTable")
    fun getCustomers(): LiveData<List<Customer>>

    @Query("SELECT * FROM CustomerTable WHERE carLicensePlate LIKE :carLicensePlate")
    fun getCustomerByLicense(carLicensePlate: String): LiveData<List<Customer>>

    @Query("SELECT * FROM CustomerTable WHERE id = :id")
    suspend fun getCustomerById(id: UUID): Customer
}