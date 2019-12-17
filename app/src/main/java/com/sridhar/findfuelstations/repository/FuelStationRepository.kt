package com.sridhar.findfuelstations.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sridhar.findfuelstations.api.WebService
import com.sridhar.findfuelstations.vo.FuelStationResponse


class FuelStationRepository(private val webService: WebService) : BaseRepository() {

    suspend fun getFuelStation(
        apiKey: String,
        fuelType: String,
        state: String,
        limit: Int
    ): LiveData<FuelStationResponse> {
        val data = MutableLiveData<FuelStationResponse>()
        data.value = callAdapter { webService.getFuelStations(apiKey, fuelType, state, limit) }
        return data
    }
}