package com.example.upstreamboredapi

import android.app.Application
import com.example.upstreamboredapi.di.PreferencesModule
import com.example.upstreamboredapi.util.SharedPreferencesHelper

class PreferencesModuleTest(val mockPreferences: SharedPreferencesHelper): PreferencesModule() {
    override fun provideSharedPreferences(appl: Application): SharedPreferencesHelper {
        return mockPreferences
    }
}