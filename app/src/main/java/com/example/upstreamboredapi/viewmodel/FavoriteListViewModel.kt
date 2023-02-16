package com.example.upstreamboredapi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.upstreamboredapi.model.AADatabase
import com.example.upstreamboredapi.model.ActionActivity
import kotlinx.coroutines.launch

class FavoriteListViewModel(application: Application) : BaseViewModel(application) {

    // LiveData to hold the list of ActionActivity objects retrieved from the database
    private val _aAList = MutableLiveData<List<ActionActivity>>()
    val aAList: LiveData<List<ActionActivity>> get() = _aAList


    private val dao = AADatabase(getApplication()).aADao()

    // Coroutine function to fetch data from the database
    fun fetchFromDB(type: String) {
        // Use a coroutine to execute database queries on a separate thread
        launch {
            // Retrieve the list of ActionActivity objects with the specified type from the database
            _aAList.value = dao.selectCat(type)
        }
    }

    fun deleteCat(type: String) {
        launch {
            dao.deleteCat(type)
        }
        Toast.makeText(getApplication(), "Successfully cleared the category list!", Toast.LENGTH_SHORT)
            .show()
    }

}
