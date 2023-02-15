package com.example.upstreamboredapi.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavArgs
import com.example.upstreamboredapi.model.AADatabase
import com.example.upstreamboredapi.model.ActionActivity
import kotlinx.coroutines.launch

class FavoriteListViewModel(application: Application) : BaseViewModel(application) {

    private val _aAList = MutableLiveData<List<ActionActivity>>()
    val aAList: LiveData<List<ActionActivity>> get() = _aAList




    fun fetchFromDB(type: String) {
        launch {
            _aAList.value = AADatabase(getApplication()).aADao().selectCat(type)
        }
    }

}