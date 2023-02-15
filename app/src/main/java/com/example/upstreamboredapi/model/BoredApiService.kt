package com.example.upstreamboredapi.model

import com.example.upstreamboredapi.di.DaggerApiComponent
import javax.inject.Inject

class BoredApiService {

    @Inject
    lateinit var api: BoredApi

    init {
        DaggerApiComponent.create().inject(this)
    }

//    private val api: BoredApi = retrofit.create(BoredApi::class.java)

    suspend fun getFilteredAction(priceMin: String, priceMax: String, type: String, accessMin: String, accessMax: String): ActionActivity {
        return api.getFilteredAction(priceMin, priceMax, type, accessMin, accessMax)
    }
}