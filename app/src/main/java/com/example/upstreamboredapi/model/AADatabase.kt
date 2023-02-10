package com.example.upstreamboredapi.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ActionActivity::class], version = 1)
abstract class AADatabase: RoomDatabase() {
    abstract fun aADao(): AADao

    companion object {
        @Volatile private var instance: AADatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AADatabase::class.java,
            "AADatabase"
        ).build()
    }
}