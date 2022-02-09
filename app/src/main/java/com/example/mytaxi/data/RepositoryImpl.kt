package com.example.mytaxi.data

import com.example.mytaxi.data.models.DirectionResponse
import com.example.mytaxi.data.models.DistanceResponse
import com.example.mytaxi.data.retrofit.GoogleApi
import com.example.mytaxi.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl(private val googleApi: GoogleApi) : Repository {

    override suspend fun getDirection(origin: String, destination: String): DirectionResponse {
        return withContext(Dispatchers.IO) {
            googleApi.getDirections(origin = origin, destination = destination)
        }
    }

    override suspend fun getDistance(origin: String, destination: String): DistanceResponse {
        return withContext(Dispatchers.IO) {
            googleApi.getDistance(origin = origin, destination = destination)
        }
    }
}