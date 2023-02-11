package com.example.upstreamboredapi.model

import android.util.Log
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

    val api = retrofit.create(BoredApi::class.java)

    suspend fun getRandomAction(): ActionActivity {
        return api.getRandomAction()
    }
    suspend fun getFilteredAction(priceMin: String, priceMax: String): ActionActivity {
        return api.getFilteredAction(priceMin, priceMax)
    }
}