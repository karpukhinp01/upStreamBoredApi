package com.example.upstreamboredapi.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.upstreamboredapi.di.DaggerStartViewModelComponent
import com.example.upstreamboredapi.di.DiAppModule
import com.example.upstreamboredapi.model.AADatabase
import com.example.upstreamboredapi.model.ActionActivity
import com.example.upstreamboredapi.util.SharedPreferencesHelper
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartViewModel(application: Application) : BaseViewModel(application) {

    constructor(application: Application, test: Boolean = true) : this(application) {
        injected = true
    }

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

    private var injected = false

    @Inject
    lateinit var prefs: SharedPreferencesHelper

    init {
        inject()
    }

    fun inject() {
        if (!injected) {
            DaggerStartViewModelComponent
                .builder()
                .diAppModule(DiAppModule(getApplication()))
                .build()
                .inject(this)
        }
    }

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

    fun deleteAll() {
        launch {
            AADatabase(getApplication()).aADao().deleteAll()
        }
    }
}