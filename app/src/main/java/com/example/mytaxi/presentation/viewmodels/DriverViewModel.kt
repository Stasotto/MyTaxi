package com.example.mytaxi.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytaxi.presentation.models.DriverModel

class DriverViewModel : ViewModel() {

    val driverData = MutableLiveData<DriverModel?>()
    val docType = MutableLiveData<String>()

}
