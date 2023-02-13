package com.example.upstreamboredapi.di

import android.app.Application
import com.example.upstreamboredapi.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(appl: Application): SharedPreferencesHelper {
        return SharedPreferencesHelper(appl)
    }
}