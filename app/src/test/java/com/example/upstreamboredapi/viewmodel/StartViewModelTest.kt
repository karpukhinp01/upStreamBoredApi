package com.example.upstreamboredapi.viewmodel

import android.app.Application
import com.example.upstreamboredapi.PreferencesModuleTest
import com.example.upstreamboredapi.di.DaggerStartViewModelComponent
import com.example.upstreamboredapi.di.DiAppModule
import com.example.upstreamboredapi.util.SharedPreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runners.model.Statement
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*

class StartViewModelTest {

    // Use a TestCoroutineDispatcher for the Main dispatcher
    private val testDispatcher = TestCoroutineDispatcher()

    // Use a JUnit TestRule to swap the Main dispatcher with the TestCoroutineDispatcher
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val testRule = TestRule { base, description ->
        object : Statement() {
            override fun evaluate() {
                Dispatchers.setMain(testDispatcher)
                base.evaluate()
                Dispatchers.resetMain()
            }
        }
    }



    @Mock
    lateinit var prefs: SharedPreferencesHelper

    val application = mock(Application::class.java)

    var startViewModel = StartViewModel(application, true)

    @Before
    fun setup() {
        DaggerStartViewModelComponent
            .builder()
            .diAppModule(DiAppModule(application))
            .preferencesModule(PreferencesModuleTest(prefs))
            .build()
            .inject(startViewModel)
    }

    @Test
    fun `set prices saves the correct price range`() {
        val priceMin = 0.2F
        val priceMax = 0.8F

        startViewModel.setPrices(priceMin, priceMax)

        verify(prefs).savePriceMin(priceMin)
        verify(prefs).savePriceMax(priceMax)
    }
}