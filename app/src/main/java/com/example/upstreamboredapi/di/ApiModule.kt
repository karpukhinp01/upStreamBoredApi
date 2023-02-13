package com.example.upstreamboredapi.di

import com.example.upstreamboredapi.model.BoredApi
import com.example.upstreamboredapi.model.BoredApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "https://www.boredapi.com/api/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun provideBoredApi(): BoredApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .baseUrl(BASE_URL)
            .build()
            .create(BoredApi::class.java)
    }

    @Provides
    fun provideBoredApiService(): BoredApiService {
        return BoredApiService()
    }
}