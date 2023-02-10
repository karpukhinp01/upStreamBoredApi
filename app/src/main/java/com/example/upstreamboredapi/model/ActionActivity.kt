package com.example.upstreamboredapi.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ActionActivity(
    val activity: String?,
    val type: String?,
    val participants: Int?,
    val price: Double?,
    val link: String?,
    @PrimaryKey
    val key: String,
    val accessibility: Double?
)
