package com.example.upstreamboredapi.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.upstreamboredapi.model.ActionActivity
import androidx.lifecycle.viewModelScope
import com.example.upstreamboredapi.di.DaggerViewModelComponent
import com.example.upstreamboredapi.di.DiAppModule
import com.example.upstreamboredapi.model.AADatabase
import com.example.upstreamboredapi.model.BoredApiService
import com.example.upstreamboredapi.util.SharedPreferencesHelper
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailViewModel(application: Application): BaseViewModel(application) {

    @Inject
    lateinit var boredService: BoredApiService

    private val _actionActivity = MutableLiveData<ActionActivity>()
    val actionActivity: LiveData<ActionActivity> get() = _actionActivity

    private val _aALoadError = MutableLiveData<Boolean>()
    val aALoadError: LiveData<Boolean> get() = _aALoadError

    @Inject
    lateinit var prefs: SharedPreferencesHelper

    init {
        DaggerViewModelComponent
            .builder()
            .diAppModule(DiAppModule(getApplication()))
            .build()
            .inject(this)
    }

    val priceMin = prefs.getPriceMin()!!.toString()
    val priceMax = prefs.getPriceMax()!!.toString()
    val type = prefs.getType()
    
    fun convertedToDollarRange(price: Double): String {
        return when {
            price == 0.0 -> "Free,"
            (0.0 < price && price <= 0.2) -> "$,"
            (0.2 < price && price <= 0.4) -> "$$,"
            (0.4 < price && price <= 0.6) -> "$$$,"
            (0.6 < price && price <= 0.8) -> "$$$$,"
            (0.8 < price && price <= 1.0) -> "$$$$$,"
            else -> "N/a,"
        }
    }

    private fun fetchFromRemote() {
        viewModelScope.launch {
            try {
                aARetrieved(boredService.getFilteredAction(priceMin, priceMax, type!!))
            } catch (e: Throwable) {
                _aALoadError.value = true
                e.printStackTrace()
            }
        }
    }

    fun storeAALocally(actionActivity: ActionActivity) {
        launch {
            val dao = AADatabase(getApplication()).aADao()
            dao.insert(actionActivity)
        }
    }

    fun refresh() {
        fetchFromRemote()
    }
    private fun aARetrieved(actionActivity: ActionActivity) {
        _actionActivity.value = actionActivity
        _aALoadError.value = false
    }


}