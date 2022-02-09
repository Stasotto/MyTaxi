package com.example.mytaxi.data.models

import com.google.gson.annotations.SerializedName

data class DirectionResponse(
    @SerializedName("routes")
    val routes: List<Route>,
    @SerializedName("status")
    val status: String

)

data class Route(
    @SerializedName("overview_polyline")
    val overViewPolyline: OverViewPolyline
)

data class OverViewPolyline(
    @SerializedName("points")
    val points: String,

)
