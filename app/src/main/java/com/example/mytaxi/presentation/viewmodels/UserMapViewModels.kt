package com.example.mytaxi.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytaxi.data.models.DirectionResponse
import com.example.mytaxi.data.models.DistanceResponse
import com.example.mytaxi.domain.usecase.GetDirectionUseCase
import com.example.mytaxi.domain.usecase.GetDistanceUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserMapViewModels(
    private val getDirectionUseCase: GetDirectionUseCase,
    private val getDistanceUseCase: GetDistanceUseCase
) : ViewModel() {
    companion object {
        var a: DistanceResponse? = null
        var b: DirectionResponse? = null
    }

    fun getDirection(origin: String, destination: String) {
        viewModelScope.launch {
            val direction = async {
                return@async getDirectionUseCase.execute(origin, destination)
            }
            val distance = async {
                return@async getDistanceUseCase.execute(origin, destination)
            }
            b = direction.await()
            a = distance.await()
        }
    }
}