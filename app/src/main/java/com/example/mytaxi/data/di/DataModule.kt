package com.example.mytaxi.data.di

import com.example.mytaxi.data.retrofit.RetrofitClient
import org.koin.dsl.module

val dataModule = module {

    single {
        RetrofitClient.getGoogleApi()
    }
}