package com.example.upstreamboredapi.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {
    companion object {
        private const val PREF_MIN_PRICE = "pref Min Price"
        private const val PREF_MAX_PRICE = "pref Max Price"
        private const val PREF_TYPE = "pref type"
        private const val PREF_BUTTON_ID = "pref button"
        private const val PREF_MIN_ACCESS = "pref Min Accessibility"
        private const val PREF_MAX_ACCESS = "pref Max Accessibility"
        private var prefs: SharedPreferences? = null

        @Volatile
        private var instance: SharedPreferencesHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper =
            instance ?: synchronized(LOCK) {
                instance ?: buildHelper(context).also {
                    instance = it
                }
            }

        private fun buildHelper(context: Context): SharedPreferencesHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    fun saveRadioButtonId(id: Int) {
        prefs?.edit(commit = true) {
            putInt(PREF_BUTTON_ID, id)
        }
    }

    fun saveType(type: String) {
        prefs?.edit(commit = true) {
            putString(PREF_TYPE, type)
        }
    }

    fun savePriceMin(priceMin: Float) {
        prefs?.edit(commit = true) {
            putFloat(PREF_MIN_PRICE, priceMin)
        }
    }

    fun savePriceMax(priceMax: Float) {
        prefs?.edit(commit = true) {
            putFloat(PREF_MAX_PRICE, priceMax)
        }
    }

    fun saveAccessMin(accessMin: Float) {
        prefs?.edit(commit = true) {
            putFloat(PREF_MIN_ACCESS, accessMin)
        }
    }

    fun saveAccessMax(accessMax: Float) {
        prefs?.edit(commit = true) {
            putFloat(PREF_MAX_ACCESS, accessMax)
        }
    }

    fun getButtonId() = prefs?.getInt(PREF_BUTTON_ID, 2929)
    fun getType() = prefs?.getString(PREF_TYPE, "")
    fun getPriceMin() = prefs?.getFloat(PREF_MIN_PRICE, 0.0F)
    fun getPriceMax() = prefs?.getFloat(PREF_MAX_PRICE, 1.0F)
    fun getAccessMin() = prefs?.getFloat(PREF_MIN_ACCESS, 0.0F)
    fun getAccessMax() = prefs?.getFloat(PREF_MAX_ACCESS, 1.0F)

}