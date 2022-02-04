package com.example.mytaxi.data.models

import com.google.android.gms.maps.model.Polyline
import com.google.gson.annotations.SerializedName

data class DirectionResponse(
    @SerializedName("routes")
    val routes: List<Route>,
    @SerializedName("status")
    val status: String

) {
}

data class Route(
    @SerializedName("overview_polyline")
    val overViewPolyline: OverViewPolyline
) {

}

data class Leg(
    @SerializedName("duration")
    val duration: String,
    @SerializedName("distance")
    val distance: String
) {

}

data class OverViewPolyline(
    @SerializedName("points")
    val points: String,

) {

}
