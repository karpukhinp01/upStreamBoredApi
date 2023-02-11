package com.example.upstreamboredapi.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {
    companion object {
        private const val PREF_MIN_PRICE = "pref Min Price"
        private const val PREF_MAX_PRICE = "pref Max Price"
        private const val PREF_TYPE = "pref type"
        private var prefs: SharedPreferences? = null

        @Volatile private var instance: SharedPreferencesHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper = instance ?: synchronized(LOCK) {
            instance ?: buildHelper(context).also {
                instance = it
            }
        }

        private fun buildHelper(context: Context): SharedPreferencesHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    fun saveType(type: String) {
        prefs?.edit(commit = true) {
            putString(PREF_TYPE, type)
            Log.d("typetype", type)
        }
    }

    fun savePriceMin(priceMin: Float) {
        prefs?.edit(commit = true) {
            putFloat(PREF_MIN_PRICE, priceMin!!)
        }
    }
    fun savePriceMax(priceMax: Float) {
        prefs?.edit(commit = true) {
            putFloat(PREF_MAX_PRICE, priceMax)
        }
    }

    fun getType() = prefs?.getString(PREF_TYPE, "")
    fun getPriceMin() = prefs?.getFloat(PREF_MIN_PRICE, 0.0F)
    fun getPriceMax() = prefs?.getFloat(PREF_MAX_PRICE, 1.0F)

}