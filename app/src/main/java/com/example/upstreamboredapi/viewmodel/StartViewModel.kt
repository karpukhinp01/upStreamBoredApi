package com.example.upstreamboredapi.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.upstreamboredapi.model.AADatabase
import com.example.upstreamboredapi.model.ActionActivity
import com.example.upstreamboredapi.util.SharedPreferencesHelper
import kotlinx.coroutines.launch

class StartViewModel(application: Application): BaseViewModel(application) {

    private val _actionActivity = MutableLiveData<ActionActivity>()
    val actionActivity: LiveData<ActionActivity> get() = _actionActivity

    private val _prices = MutableLiveData<List<String>>()
    val prices: LiveData<List<String>> get() = _prices

    private val _buttonId = MutableLiveData<Int>()
    val buttonId: LiveData<Int> get() = _buttonId

    val type = SharedPreferencesHelper(getApplication()).getType()

    fun setPrices(priceMin: Float, priceMax: Float) {
        SharedPreferencesHelper(getApplication()).savePriceMin(priceMin)
        SharedPreferencesHelper(getApplication()).savePriceMax(priceMax)
    }

    fun setTypes(type: String?, buttonId: Int) {
        type?.let {
            SharedPreferencesHelper(getApplication()).saveType(it)
        }
        SharedPreferencesHelper(getApplication()).saveRadioButtonId(buttonId)
    }

    fun resetFilters() {
        SharedPreferencesHelper(getApplication()).apply {
            savePriceMin(0.0F)
            savePriceMax(1.0F)
            saveType("")
            saveRadioButtonId(2929)
        }
    }

    fun setFilterValuesToDialog() {
        _prices.value = listOf(SharedPreferencesHelper(getApplication()).getPriceMin().toString(),
         SharedPreferencesHelper(getApplication()).getPriceMax().toString())
        _buttonId.value = SharedPreferencesHelper(getApplication()).getButtonId()
    }

    fun deleteAll() {
        launch {
            AADatabase(getApplication()).aADao().deleteAll()
        }
    }


}