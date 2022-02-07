package io.blacketron.garagesystem.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "CustomerTable")
data class Customer (

    @PrimaryKey()
    val id: String = UUID.randomUUID().toString(),
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val carManufacturer: String,
    val carModel: String,
    val carLicensePlate: String,
    val duration: Int
)