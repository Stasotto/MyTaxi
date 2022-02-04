package com.example.mytaxi.presentation.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DriverLiveData : ViewModel() {

    val driverData = MutableLiveData<DriverModel?>()
    val docType = MutableLiveData<String>()

}
