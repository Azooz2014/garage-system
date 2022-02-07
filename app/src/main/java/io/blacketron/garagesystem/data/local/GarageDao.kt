package io.blacketron.garagesystem.data.local

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
    suspend fun getCustomers(): List<Customer>

    @Query("SELECT * FROM CustomerTable WHERE carLicensePlate = :carLicensePlate")
    suspend fun getCustomerByLicense(carLicensePlate: String): Customer

    @Query("SELECT * FROM CustomerTable WHERE id = :id")
    suspend fun getCustomerById(id: UUID): Customer
}