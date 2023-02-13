package com.example.upstreamboredapi.di

import com.example.upstreamboredapi.viewmodel.StartViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [PreferencesModule::class, DiAppModule::class])
interface StartViewModelComponent {

    fun inject(startViewModel: StartViewModel)
}