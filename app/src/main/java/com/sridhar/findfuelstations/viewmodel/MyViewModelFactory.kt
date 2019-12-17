package com.sridhar.findfuelstations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sridhar.findfuelstations.repository.FuelStationRepository


class MyViewModelFactory constructor(private val repository: FuelStationRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FuelStationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            FuelStationViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}