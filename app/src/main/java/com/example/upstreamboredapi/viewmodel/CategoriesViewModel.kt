package com.example.upstreamboredapi.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.upstreamboredapi.model.AADatabase
import kotlinx.coroutines.launch

class CategoriesViewModel(application: Application) : BaseViewModel(application) {

    private val _mCategories = MutableLiveData<List<String>>()
    val mCategories: LiveData<List<String>> get() = _mCategories

    private val dao = AADatabase(getApplication()).aADao()

    fun fetchCategoriesFromDb() {
        launch {
            _mCategories.value = dao.selectCat()
        }
    }

}