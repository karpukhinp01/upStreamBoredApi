package com.example.upstreamboredapi.model

import retrofit2.http.GET
import retrofit2.http.Query

interface BoredApi {

    @GET("activity")
    suspend fun getFilteredAction(
        @Query("minprice", encoded = true) filter1: String,
        @Query("maxprice", encoded = true) filter2: String,
        @Query("type", encoded = true) type: String,
        @Query("minaccessibility", encoded = true) accessMin: String,
        @Query("maxaccessibility", encoded = true) accessMax: String
    ): ActionActivity
}