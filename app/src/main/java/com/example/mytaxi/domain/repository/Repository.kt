package com.example.mytaxi.domain.repository

import com.example.mytaxi.data.models.DirectionResponse
import com.example.mytaxi.data.models.DistanceResponse

interface Repository {

    suspend fun getDirection(origin: String, destination: String): DirectionResponse

    suspend fun getDistance(origin: String, destination: String): DistanceResponse
}