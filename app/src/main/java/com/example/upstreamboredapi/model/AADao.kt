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

    @Query("SELECT * FROM ActionActivity WHERE TYPE=:type")
    suspend fun selectCat(type: String): List<ActionActivity>

    @Query("DELETE FROM ActionActivity WHERE TYPE=:type")
    suspend fun deleteCat(type: String)

    @Query("SELECT DISTINCT type FROM ActionActivity")
    suspend fun selectCat(): List<String>
}