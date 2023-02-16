package com.example.upstreamboredapi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

// Define an abstract class named BaseViewModel that extends AndroidViewModel and implements CoroutineScope
abstract class BaseViewModel(Application: Application) : AndroidViewModel(Application),
    CoroutineScope {

    // Create a Job instance
    private val job = Job()

    // Override the coroutineContext property to return the job and Dispatchers.Main
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    // Override the onCleared() method to cancel the job
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}