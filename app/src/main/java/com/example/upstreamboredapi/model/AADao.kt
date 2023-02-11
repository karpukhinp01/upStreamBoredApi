package com.example.upstreamboredapi.model

import androidx.room.*


@Dao
interface AADao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(actionActivity: ActionActivity)

    @Delete
    suspend fun delete(actionActivity: ActionActivity)

    @Query("DELETE FROM ActionActivity")
    suspend fun deleteAll()

    @Query("SELECT * FROM ActionActivity")
    suspend fun selectAll(): List<ActionActivity>
}