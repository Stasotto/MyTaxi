package com.example.mytaxi.domain.usecase

import com.example.mytaxi.data.models.DistanceResponse
import com.example.mytaxi.domain.repository.Repository

class GetDistanceUseCase(private val repository: Repository) {

    suspend fun execute(origin: String, destination: String): DistanceResponse {
        return repository.getDistance(origin, destination)
    }
}