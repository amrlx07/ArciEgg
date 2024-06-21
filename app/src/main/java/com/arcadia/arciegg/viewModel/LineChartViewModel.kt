package com.arcadia.arciegg.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcadia.arciegg.repository.SensorListRepository
import com.arcadia.arciegg.uiState.SensorData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LineChartViewModel : ViewModel() {
    private val repository = SensorListRepository()
    val monitoringData : LiveData<List<SensorData>> = repository.fetchSensorData()

    init {
        fetchSensorData()
        scheduleDataDeletion()
    }
    private fun fetchSensorData() {
        repository.fetchSensorData()
    }
    private fun scheduleDataDeletion(){
        viewModelScope.launch {
            while (true) {
                delay(300000)// 5 menit
                repository.deleteSensorData()
            }
        }
    }
}