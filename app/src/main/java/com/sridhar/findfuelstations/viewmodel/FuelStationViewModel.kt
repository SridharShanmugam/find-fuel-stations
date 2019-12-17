package com.sridhar.findfuelstations.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sridhar.findfuelstations.repository.FuelStationRepository
import com.sridhar.findfuelstations.vo.FuelStationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FuelStationViewModel(private val repository: FuelStationRepository) : ViewModel() {

    private val _fuelStationResponse = MutableLiveData<FuelStationResponse>()
    internal val fuelStationResponse: LiveData<FuelStationResponse> = _fuelStationResponse

    internal fun getFuelStations() {
        viewModelScope.launch {
            val response = repository.getFuelStation(
                "DEMO_KEY",
                "E85,ELEC",
                "CA",
                100
            )
            withContext(Dispatchers.Main) {
                if (response.value != null)
                    _fuelStationResponse.value = response.value
            }
        }
    }
}