package com.example.upstreamboredapi.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.upstreamboredapi.model.ActionActivity
import androidx.lifecycle.viewModelScope
import com.example.upstreamboredapi.model.AADatabase
import com.example.upstreamboredapi.model.BoredApiService
import com.example.upstreamboredapi.util.SharedPreferencesHelper
import kotlinx.coroutines.launch


class DetailViewModel(application: Application): BaseViewModel(application) {

    private val boredService = BoredApiService()

    private val _actionActivity = MutableLiveData<ActionActivity>()
    val actionActivity: LiveData<ActionActivity> get() = _actionActivity

    private val _aALoadError = MutableLiveData<Boolean>()
    val aALoadError: LiveData<Boolean> get() = _aALoadError

    val priceMin = SharedPreferencesHelper(getApplication()).getPriceMin().toString()
    val priceMax = SharedPreferencesHelper(getApplication()).getPriceMax().toString()
    val type = SharedPreferencesHelper(getApplication()).getType()

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