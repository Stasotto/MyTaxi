package com.example.mytaxi.data

import android.webkit.WebStorage
import com.example.mytaxi.data.models.DirectionResponse
import com.example.mytaxi.data.models.DistanceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsApi {
//    https://maps.googleapis.com/maps/api/directions/json?origin=Disneyland&destination=Universal+Studios+Hollywood&key=YOUR_API_KEY

    @GET("maps/api/directions/json")
  suspend fun getDirections(
        @Query("mode") mode: String = "driving",
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String = RetrofitClient.API_KEY
  ) : DirectionResponse

  @GET("maps/api/distancematrix/json")
  suspend fun getDistance(
      @Query("mode") mode: String = "driving",
      @Query("origins") origin: String,
      @Query("destinations") destination: String,
      @Query("key") key: String = RetrofitClient.API_KEY
  ): DistanceResponse
}

