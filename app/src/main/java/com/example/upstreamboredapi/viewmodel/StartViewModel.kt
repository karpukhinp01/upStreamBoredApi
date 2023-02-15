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

    private val _buttonId = MutableLiveData<Int>()
    val buttonId: LiveData<Int> get() = _buttonId

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

    val type = prefs.getType()


    fun setPrices(priceMin: Float, priceMax: Float) {
        prefs.savePriceMin(priceMin)
        prefs.savePriceMax(priceMax)
    }

    fun setTypes(type: String?, buttonId: Int) {
        type?.let {
            prefs.saveType(it)
        }
        prefs.saveRadioButtonId(buttonId)
    }

    fun resetFilters() {
        prefs.apply {
            savePriceMin(0.0F)
            savePriceMax(1.0F)
            saveType("")
            saveRadioButtonId(2929)
        }
    }

    fun setFilterValuesToDialog() {
        _prices.value = listOf(
            prefs.getPriceMin().toString(),
            prefs.getPriceMax().toString()
        )
        _buttonId.value = prefs.getButtonId()
    }

    fun deleteAll() {
        launch {
            AADatabase(getApplication()).aADao().deleteAll()
        }
    }
}