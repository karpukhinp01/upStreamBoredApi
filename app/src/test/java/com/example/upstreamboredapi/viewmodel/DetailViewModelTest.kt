package com.example.upstreamboredapi.viewmodel

import android.app.Application
import com.example.upstreamboredapi.ApiModuleTest
import com.example.upstreamboredapi.PreferencesModuleTest
import com.example.upstreamboredapi.di.DaggerViewModelComponent
import com.example.upstreamboredapi.di.DiAppModule
import com.example.upstreamboredapi.model.AADao
import com.example.upstreamboredapi.model.ActionActivity
import com.example.upstreamboredapi.model.BoredApiService
import com.example.upstreamboredapi.util.SharedPreferencesHelper
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runners.model.Statement
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.*

class DetailViewModelTest {

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
    lateinit var boredApiService: BoredApiService

    @Mock
    lateinit var prefs: SharedPreferencesHelper

    val application = mock(Application::class.java)

    var detailViewModel = DetailViewModel(application, true)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        DaggerViewModelComponent
            .builder()
            .diAppModule(DiAppModule(application))
            .apiModule(ApiModuleTest(boredApiService))
            .preferencesModule(PreferencesModuleTest(prefs))
            .build()
            .inject(detailViewModel)
    }

    @Test
    fun testConvertedToDollarRange() {
        assertEquals("Free,", detailViewModel.convertedToDollarRange(0.0))
        assertEquals("$,", detailViewModel.convertedToDollarRange(0.1))
        assertEquals("$$,", detailViewModel.convertedToDollarRange(0.3))
        assertEquals("$$$$$,", detailViewModel.convertedToDollarRange(0.9))
        assertEquals("N/a,", detailViewModel.convertedToDollarRange(1.1))
    }

    @Test
    fun testStoreAALocally() {
        val mockDao = mock(AADao::class.java)
        val mockAA = ActionActivity(activity = "Mock activity", null, null, null, null, "0", null)
        detailViewModel.launch {
            detailViewModel.storeAALocally(mockAA)
            verify(mockDao).insert(mockAA)
        }
    }
}