package com.example.upstreamboredapi.di

import android.app.Application
import dagger.Module
import dagger.Provides


@Module
class DiAppModule(val appl: Application) {
    @Provides
    fun provideApp(): Application = appl
}