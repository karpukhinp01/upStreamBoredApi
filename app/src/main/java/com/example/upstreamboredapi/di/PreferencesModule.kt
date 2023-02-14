package com.example.upstreamboredapi.di

import android.app.Application
import com.example.upstreamboredapi.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class PreferencesModule {

    @Provides
    @Singleton
    open fun provideSharedPreferences(appl: Application): SharedPreferencesHelper {
        return SharedPreferencesHelper(appl)
    }
}