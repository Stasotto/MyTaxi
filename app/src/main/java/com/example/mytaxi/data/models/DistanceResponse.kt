package com.example.mytaxi.data.models

data class DistanceResponse(
    val destinationAddress: List<String>,
    val originAddress: List<String>,
    val rows: List<Rows>,
    val status: String
)

data class Rows(
    val elements: List<Elements>

)

data class Elements(
    val distance: Distance,
    val duration: Duration,
    val status: String
)

data class Duration(
    val text: String,
    val value: Int
)

data class Distance(
    val text: String,
    val value: Int
)
