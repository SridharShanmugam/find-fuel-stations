package com.sridhar.findfuelstations.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sridhar.findfuelstations.ui.FuelStationActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashViewModel(application: Application) : AndroidViewModel(application) {

    internal val _isActivityLaunched: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        viewModelScope.launch {
            delay(3000)
            withContext(Dispatchers.Main) {
                application.startActivity(
                    Intent(
                        application.applicationContext,
                        FuelStationActivity::class.java
                    )
                )
                _isActivityLaunched.value = true
            }
        }
    }
}