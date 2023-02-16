package com.example.upstreamboredapi.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.upstreamboredapi.di.DaggerStartViewModelComponent
import com.example.upstreamboredapi.di.DiAppModule
import com.example.upstreamboredapi.model.ActionActivity
import com.example.upstreamboredapi.util.SharedPreferencesHelper
import javax.inject.Inject

// This is a Kotlin class for the StartViewModel. It extends the BaseViewModel class.
class StartViewModel(application: Application) : BaseViewModel(application) {

    // Secondary constructor to be used for testing purposes
    constructor(application: Application, test: Boolean = true) : this(application) {
        injected = true
    }

    // Define several LiveData objects
    private val _actionActivity = MutableLiveData<ActionActivity>()
    val actionActivity: LiveData<ActionActivity> get() = _actionActivity

    private val _prices = MutableLiveData<List<String>>()
    val prices: LiveData<List<String>> get() = _prices

    private val _access = MutableLiveData<List<Float>>()
    val access: LiveData<List<Float>> get() = _access

    private val _buttonId = MutableLiveData<Int>()
    val buttonId: LiveData<Int> get() = _buttonId

    private val _type = MutableLiveData<String>()
    val type: LiveData<String> get() = _type

    // Private property to keep track of whether injection has occurred
    private var injected = false

    // Inject SharedPreferencesHelper using Dagger
    @Inject
    lateinit var prefs: SharedPreferencesHelper

    // Call the inject function to perform injection
    init {
        inject()
    }

    // Check if injection has occurred and perform injection if it hasn't
    fun inject() {
        if (!injected) {
            DaggerStartViewModelComponent
                .builder()
                .diAppModule(DiAppModule(getApplication()))
                .build()
                .inject(this)
        }
    }

    // Functions for setting filter values
    fun setPrices(priceMin: Float, priceMax: Float) {
        prefs.savePriceMin(priceMin)
        prefs.savePriceMax(priceMax)
    }

    fun setAccess(accessMin: Float, accessMax: Float) {
        prefs.saveAccessMin(accessMin)
        prefs.saveAccessMax(accessMax)
    }

    fun setTypes(type: String?, buttonId: Int) {
        type?.let {
            prefs.saveType(it)
        }
        prefs.saveRadioButtonId(buttonId)
    }

    // Function to reset filter values to their default values
    fun resetFilters() {
        prefs.apply {
            saveAccessMin(0.0F)
            saveAccessMax(1.0F)
            savePriceMin(0.0F)
            savePriceMax(1.0F)
            saveType("")
            saveRadioButtonId(2929)
        }
    }

    // Function to set the filter values to be displayed in the filter dialog
    fun setFilterValuesToDialog() {
        _access.value = listOf(
            prefs.getAccessMin()!!,
            prefs.getAccessMax()!!
        )
        _prices.value = listOf(
            prefs.getPriceMin().toString(),
            prefs.getPriceMax().toString()
        )
        _buttonId.value = prefs.getButtonId()
        _type.value = prefs.getType()
    }

}
