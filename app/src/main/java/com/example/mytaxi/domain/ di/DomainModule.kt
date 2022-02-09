package com.example.mytaxi.domain.di

import com.example.mytaxi.data.RepositoryImpl
import com.example.mytaxi.domain.usecase.GetDirectionUseCase
import com.example.mytaxi.domain.repository.Repository
import com.example.mytaxi.domain.usecase.GetDistanceUseCase
import org.koin.dsl.module


val domainModule = module {

    single<Repository> {
        RepositoryImpl(googleApi = get())
    }

    single {
        GetDistanceUseCase(repository = get())
    }

    single {
        GetDirectionUseCase(repository = get())
    }
}