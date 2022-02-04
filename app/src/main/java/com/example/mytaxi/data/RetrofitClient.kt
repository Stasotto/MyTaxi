package com.example.mytaxi.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val API_KEY = "AIzaSyBql8HcWnoE_0nA1lT3tbs8N4sfC6vokb8"
    const val BASE_URL = "https://maps.googleapis.com/"

    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    fun getDirectionApi(): DirectionsApi = getClient().create(DirectionsApi::class.java)

private fun getClient() = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()
}