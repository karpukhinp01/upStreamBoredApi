package com.example.upstreamboredapi.model

import retrofit2.http.GET
import retrofit2.http.Path

interface BoredApi {
    @GET("activity")
    suspend fun getRandomAction(): ActionActivity

    @GET("activity{filter1}")
    suspend fun getFilteredAction(@Path("filter1") filter1: String?): ActionActivity
}