package com.example.upstreamboredapi.viewmodel

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.upstreamboredapi.model.ActionActivity
import androidx.lifecycle.viewModelScope
import com.example.upstreamboredapi.R
import com.example.upstreamboredapi.di.DaggerViewModelComponent
import com.example.upstreamboredapi.di.DiAppModule
import com.example.upstreamboredapi.model.AADatabase
import com.example.upstreamboredapi.model.BoredApiService
import com.example.upstreamboredapi.util.SharedPreferencesHelper
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailViewModel(application: Application): BaseViewModel(application) {

    constructor(application: Application, test: Boolean = true): this(application) {
        injected = true
    }

    // Injecting the BoredApiService and SharedPreferencesHelper using Dagger
    @Inject
    lateinit var boredService: BoredApiService
    @Inject
    lateinit var prefs: SharedPreferencesHelper

    // LiveData for ActionActivity object, loading error and error message
    private val _actionActivity = MutableLiveData<ActionActivity>()
    val actionActivity: LiveData<ActionActivity> get() = _actionActivity
    private val _aALoadError = MutableLiveData<Boolean>()
    val aALoadError: LiveData<Boolean> get() = _aALoadError
    private val _aALoadErrorMessage = MutableLiveData<String>()
    val aALoadErrorMessage: LiveData<String> get() = _aALoadErrorMessage

    private var injected = false

    // Initialize the Dagger component
    init {
        inject()
    }

    // Inject dependencies using Dagger
    fun inject() {
        if (!injected) {
            DaggerViewModelComponent
                .builder()
                .diAppModule(DiAppModule(getApplication()))
                .build()
                .inject(this)
        }
    }

    val priceMin = prefs.getPriceMin()?.toString() ?: "0.0"
    val priceMax = prefs.getPriceMax()?.toString() ?: "1.0"
    val accessMin = prefs.getAccessMin()?.toString() ?: "0.0"
    val accessMax = prefs.getPriceMax()?.toString() ?: "1.0"
    val type = prefs.getType()


    // Convert the price to a dollar range string
    fun convertedToDollarRange(price: Double): String {
        return when {
            price == 0.0 -> "Free"
            (0.0 < price && price <= 0.2) -> "$"
            (0.2 < price && price <= 0.4) -> "$$"
            (0.4 < price && price <= 0.6) -> "$$$"
            (0.6 < price && price <= 0.8) -> "$$$$"
            (0.8 < price && price <= 1.0) -> "$$$$$"
            else -> "N/a,"
        }
    }

    // Fetch data from the remote API using the BoredApiService and the filters from SharedPreferences
    private fun fetchFromRemote() {
        viewModelScope.launch {
            try {
                aARetrieved(boredService.getFilteredAction(priceMin, priceMax, type!!, accessMin, accessMax))
            } catch (e: java.net.UnknownHostException) {
                _aALoadErrorMessage.value = "Internet connection error, please try again later"
                _aALoadError.value = true
                e.printStackTrace()
            } catch (e: com.squareup.moshi.JsonDataException) {
                _aALoadErrorMessage.value = "There are no available cards for the set of filters"
                _aALoadError.value = true
                e.printStackTrace()
            }
        }
    }

    fun storeAALocally(actionActivity: ActionActivity) {
        // launch a coroutine to insert the actionActivity into the database
        launch {
            if (!actionActivity.type.equals("instruction")) {
                val dao = AADatabase(getApplication()).aADao()
                dao.insert(actionActivity)
            }
        }
    }

    fun refresh() {
        // fetch data from remote source
        fetchFromRemote()
    }

    // callback for when data is retrieved from remote source
    private fun aARetrieved(actionActivity: ActionActivity) {
        // update the LiveData variables with the retrieved data
        _actionActivity.value = actionActivity
        _aALoadError.value = false
    }

    fun setAccessibilityParams(textView: TextView, accessibility: Double) {
        textView.apply {
            // set the text and style of the TextView based on the accessibility value
            when {
                accessibility == 0.0 -> {
                    setText(R.string.basic)
                    setTextAppearance(R.style.color_easy)
                }
                (0.0 < accessibility && accessibility <= 0.2) -> {
                    setText(R.string.very_easy)
                    setTextAppearance(R.style.color_easy)
                }
                (0.2 < accessibility && accessibility <= 0.4) -> {
                    setText(R.string.easy)
                    setTextAppearance(R.style.color_easy)
                }
                (0.4 < accessibility && accessibility <= 0.6) -> {
                    setText(R.string.medium)
                    setTextAppearance(R.style.color_medium)
                }
                (0.6 < accessibility && accessibility <= 0.8) -> {
                    setText(R.string.medium)
                    setTextAppearance(R.style.color_hard)
                }
                (0.8 < accessibility && accessibility <= 1.0) -> {
                    setText(R.string.very_hard)
                    setTextAppearance(R.style.color_hard)
                }
                else -> {
                    text = "N/a"
                }
            }
        }
    }


}