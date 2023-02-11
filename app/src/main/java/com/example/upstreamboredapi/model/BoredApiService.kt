package com.example.upstreamboredapi.model

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class BoredApiService {

    private val BASE_URL = "https://www.boredapi.com/api/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .baseUrl(BASE_URL)
        .build()

    private val api: BoredApi = retrofit.create(BoredApi::class.java)

    suspend fun getFilteredAction(priceMin: String, priceMax: String, type: String): ActionActivity {
        return api.getFilteredAction(priceMin, priceMax, type)
    }
}