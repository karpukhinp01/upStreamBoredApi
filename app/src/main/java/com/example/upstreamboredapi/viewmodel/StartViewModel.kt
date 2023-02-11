package com.example.upstreamboredapi.viewmodel

import android.app.Application
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.upstreamboredapi.model.AADatabase
import com.example.upstreamboredapi.model.ActionActivity
import com.example.upstreamboredapi.model.BoredApiService
import com.example.upstreamboredapi.util.SharedPreferencesHelper
import kotlinx.coroutines.launch

class StartViewModel(application: Application): BaseViewModel(application) {

    private val boredService = BoredApiService()

    private val _actionActivity = MutableLiveData<ActionActivity>()
    val actionActivity: LiveData<ActionActivity> get() = _actionActivity

    private var _filterPriceRange = "?minprice=0.0&maxprice=1.0"
    val filterPriceRange: String get() =_filterPriceRange

    private val _aALoadError = MutableLiveData<Boolean>()
    val aALoadError: LiveData<Boolean> get() = _aALoadError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    val priceMin = SharedPreferencesHelper(getApplication()).getPriceMin().toString()
    val priceMax = SharedPreferencesHelper(getApplication()).getPriceMax().toString()

    fun setFilters(priceMin: Float, priceMax: Float) {
        SharedPreferencesHelper(getApplication()).savePriceMin(priceMin)
        SharedPreferencesHelper(getApplication()).savePriceMax(priceMax)
    }

    fun deleteAll() {
        launch {
            AADatabase(getApplication()).aADao().deleteAll()
        }
    }


}