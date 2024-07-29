package com.arcadia.arciegg.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.arcadia.arciegg.repository.SensorListRepository
import com.arcadia.arciegg.uiState.MonitoringUiState
import com.arcadia.arciegg.uiState.SensorCheckUiState
import com.arcadia.arciegg.uiState.SetPointUiState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.roundToInt

class DetailMonitoringViewModel : ViewModel() {
    //repository d
    private val repository = SensorListRepository()
    private val databasePathMonitoring = repository.databaseMonitoring
    private val databasePathSensorCheck = repository.databaseSensorCheck
    private val databasePathSetpoint = repository.databaseSetpoint

    //monitoring state
    private val _stateMonitor = MutableStateFlow(MonitoringUiState())
    val stateMonitor: StateFlow<MonitoringUiState?> = _stateMonitor.asStateFlow()
    //setpoint state
    private val _stateSetPoint = MutableStateFlow(SetPointUiState())
    val stateSetPoint: StateFlow<SetPointUiState?> = _stateSetPoint.asStateFlow()
    //sensor check state
    private val _stateSensorCheck = MutableStateFlow(SensorCheckUiState())
    val stateSensorCheck: StateFlow<SensorCheckUiState?> = _stateSensorCheck.asStateFlow()

    private val _sensorCheckUiState = MutableStateFlow(SensorCheckUiState())
    val sensorCheckUiState: StateFlow<SensorCheckUiState> = _sensorCheckUiState.asStateFlow()


    fun roundup(formatted: Double?): Double {
        val roundup = formatted!! * 100.0
        return roundup.roundToInt() / 100.0
    }

    init {
        fetchMonitoringData()
        fetchSetPointData()
        fetchSensorCheck()
    }
    private fun fetchMonitoringData() {
        databasePathMonitoring.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("FirebaseMonitoring", "Successfully to fetch data.")
                val getDataMonitoring = snapshot.getValue<MonitoringUiState?>()
                _stateMonitor.update {
                    it.copy(
                        Suhu = getDataMonitoring?.Suhu ?: 0.0,
                        Kelembapan = getDataMonitoring?.Kelembapan ?: 0.0,
                    )
                }
                Log.d("FirebaseMonitoring", "Suhu: ${_stateMonitor.value.Suhu}")
                Log.d("FirebaseMonitoring", "Kelembapan: ${_stateMonitor.value.Kelembapan}")
            }

            override fun onCancelled(error: DatabaseError) {
                _stateMonitor.update {
                    it.copy(
                        Suhu = 0.0,
                        Kelembapan = 0.0
                    )
                }
            }
        })
    }
    private fun fetchSetPointData() {
        databasePathSetpoint.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("FirebaseSetPoint", "Successfully to fetch data.")
                val getDataSetPoint = snapshot.getValue<SetPointUiState?>()
                _stateSetPoint.update {
                    it.copy(
                        Suhu = getDataSetPoint?.Suhu ?: 0,
                        Kelembapan = getDataSetPoint?.Kelembapan ?: 0,
                    )
                }
                Log.d("FirebaseSetpoint", "Suhu: ${_stateSetPoint.value.Suhu}")
                Log.d("FirebaseSetpoint", "Kelembapan: ${_stateSetPoint.value.Kelembapan}")
            }
            override fun onCancelled(error: DatabaseError) {
                _stateSetPoint.update {
                    it.copy(
                        Suhu = 0,
                        Kelembapan = 0
                    )
                }
            }
        })
    }
    private fun fetchSensorCheck() {
        databasePathSensorCheck.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val sensorData = snapshot.getValue(SensorCheckUiState::class.java) ?: SensorCheckUiState()
            _sensorCheckUiState.value = sensorData
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle error appropriately, e.g., log or show a message
        }
    })
    }
}



