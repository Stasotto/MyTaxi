package com.example.mytaxi.presentation

import android.app.Application
import com.example.mytaxi.data.di.dataModule
import com.example.mytaxi.domain.di.domainModule
import com.example.mytaxi.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyTaxiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {

        startKoin {
            androidContext(this@MyTaxiApplication)
            modules(
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}