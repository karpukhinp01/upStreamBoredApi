package com.example.upstreamboredapi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.upstreamboredapi.model.ActionActivity
import androidx.lifecycle.viewModelScope
import com.example.upstreamboredapi.model.BoredApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class DetailViewModel(application: Application): BaseViewModel(application) {

    private val _cardsArrayList = MutableLiveData<ArrayList<String>>()
    val cardsArrayList: LiveData<ArrayList<String>> get() = _cardsArrayList

    private val boredService = BoredApiService()

    private val _actionActivity = MutableLiveData<ActionActivity>()
    val actionActivity: LiveData<ActionActivity> get() = _actionActivity

    private val _aALoadError = MutableLiveData<Boolean>()
    val aALoadError: LiveData<Boolean> get() = _aALoadError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private fun fetchFromRemote() {
        _loading.value = true
        viewModelScope.launch {
            try {
                aARetrieved(boredService.getRandomAction())
                Toast.makeText(getApplication(), "Activities retrieved from remote", Toast.LENGTH_SHORT).show()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    private fun aARetrieved(actionActivity: ActionActivity) {
        _actionActivity.value = actionActivity

        _loading.value = false
        _aALoadError.value = false
    }
}