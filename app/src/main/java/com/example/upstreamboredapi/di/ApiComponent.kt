package com.example.upstreamboredapi.di

import com.example.upstreamboredapi.model.BoredApiService
import dagger.Component


@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: BoredApiService)
}