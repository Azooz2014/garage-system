package io.blacketron.garagesystem.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.blacketron.garagesystem.model.Customer

@Database(entities = [Customer::class], version = 1)
abstract class GarageDB: RoomDatabase() {

    abstract fun garageDao(): GarageDao

    companion object {
        private const val DATABASE_NAME = "garage.db"

        @Volatile
        private var instance: GarageDB? = null

        fun getInstance(context: Context): GarageDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): GarageDB {
            return Room.databaseBuilder(context, GarageDB::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}