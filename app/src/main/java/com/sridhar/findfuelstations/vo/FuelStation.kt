package com.sridhar.findfuelstations.vo

import com.google.gson.annotations.SerializedName


data class FuelStationResponse(
    @SerializedName("fuel_stations") val fuelStations: List<FuelStation>
)

data class FuelStation(
    @SerializedName("station_name") val stationName: String,
    @SerializedName("station_phone") val stationPhone: String?,
    val city: String
)