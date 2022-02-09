package com.example.mytaxi.data.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    const val API_KEY = "key"
    private const val BASE_URL = "https://maps.googleapis.com/"

    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    fun getGoogleApi(): GoogleApi = getClient().create(GoogleApi::class.java)


    private fun getClient() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}