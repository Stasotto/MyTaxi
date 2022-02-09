package com.example.mytaxi.presentation.di

import com.example.mytaxi.presentation.viewmodels.UserMapViewModels
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        UserMapViewModels(getDirectionUseCase = get(), getDistanceUseCase = get())
    }
}