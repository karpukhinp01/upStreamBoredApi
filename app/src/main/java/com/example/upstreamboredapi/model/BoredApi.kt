package com.example.upstreamboredapi.model

import retrofit2.http.GET

interface BoredApi {
    @GET("activity")
    suspend fun getRandomAction(): ActionActivity
}