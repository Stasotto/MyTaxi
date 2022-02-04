package com.example.mytaxi.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytaxi.data.RetrofitClient
import com.example.mytaxi.data.models.DirectionResponse
import com.example.mytaxi.data.models.DistanceResponse
import com.google.android.gms.tasks.Tasks.await
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserMapViewModels : ViewModel() {
    companion object {
        var a: DistanceResponse? = null
        var b: DirectionResponse? = null
    }
//    private val _distance = MutableLiveData<DistanceResponse>()
//    val distance: LiveData<DistanceResponse> get() = _distance
//
//    private val _direction = MutableLiveData<DirectionResponse>()
//    val direction: LiveData<DirectionResponse> get() = _direction

    fun getDirection(origin: String, destination: String) {
        viewModelScope.launch {
            val direction = async {
                return@async RetrofitClient.getDirectionApi()
                    .getDirections(origin = origin, destination = destination)
            }
            val distance = async {
                return@async RetrofitClient.getDirectionApi()
                    .getDistance(origin = origin, destination = destination)
            }
            b = direction.await()
            a = distance.await()
        }
    }
}