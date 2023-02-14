package com.example.upstreamboredapi

import com.example.upstreamboredapi.di.ApiModule
import com.example.upstreamboredapi.model.BoredApiService

class ApiModuleTest(val mockService: BoredApiService): ApiModule() {
    override fun provideBoredApiService(): BoredApiService {
        return mockService
    }
}