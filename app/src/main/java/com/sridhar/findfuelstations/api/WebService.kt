package com.sridhar.findfuelstations.api

import com.sridhar.findfuelstations.vo.FuelStationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WebService {

    @GET("api/alt-fuel-stations/v1.json")
    suspend fun getFuelStations(
        @Query("api_key") apiKey: String,
        @Query("fuel_type") fuelType: String,
        @Query("state") state: String,
        @Query("limit") limit: Int
    ): Response<FuelStationResponse>
}