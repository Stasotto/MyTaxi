package com.example.mytaxi.domain.usecase

import com.example.mytaxi.data.models.DirectionResponse
import com.example.mytaxi.domain.repository.Repository

class GetDirectionUseCase(private val repository: Repository) {

    suspend fun execute(origin: String, destination: String): DirectionResponse {
        return repository.getDirection(origin, destination)
    }
}