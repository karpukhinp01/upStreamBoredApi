package com.example.upstreamboredapi.di

import com.example.upstreamboredapi.viewmodel.DetailViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApiModule::class, PreferencesModule::class, DiAppModule::class])
interface ViewModelComponent {

    fun inject(viewModel: DetailViewModel)
}