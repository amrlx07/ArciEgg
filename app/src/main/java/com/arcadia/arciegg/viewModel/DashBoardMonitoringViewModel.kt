package com.arcadia.arciegg.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.arcadia.arciegg.repository.SensorListRepository
import com.arcadia.arciegg.uiState.ControlUiState
import com.arcadia.arciegg.uiState.MonitoringUiState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.roundToInt

class DashBoardMonitoringViewModel() : ViewModel() {

    private val repository = SensorListRepository()
    private val databasePathMonitoring = repository.databaseMonitoring
    private val databasePathControl = repository.databaseControl

    private val _stateMonitor = MutableStateFlow(MonitoringUiState())
    val stateMonitor: StateFlow<MonitoringUiState?> = _stateMonitor.asStateFlow()

    private val _stateControl = MutableStateFlow(ControlUiState())
    val stateControl : StateFlow<ControlUiState?> = _stateControl.asStateFlow()

    fun roundup(formatted: Double?): Double {
        val roundup = formatted!! * 100.0
        return roundup.roundToInt() / 100.0
    }

    fun condition( logic: Int? ) : String {
        var condition = ""
        when (logic) {
            0 -> condition = "OFF"
            1 -> condition = "ON"
            else -> condition = "ERROR"
        }
        return condition
    }

    private fun fetchControlData() {
        databasePathControl.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("FirebaseControl", "Successfully to fetch data.")
                val getDataControl = snapshot.getValue<ControlUiState?>()
                _stateControl.update {
                    it.copy(
                        Fan = getDataControl?.Fan ?: 0,
                        Heater = getDataControl?.Heater ?: 0,
                        Sprayer = getDataControl?.Sprayer ?: 0
                    )
                }
                Log.d("FirebaseControl", "Fan: ${_stateControl.value.Fan}")
                Log.d("FirebaseControl", "Heater: ${_stateControl.value.Heater}")
                Log.d("FirebaseControl", "Sprayer: ${_stateControl.value.Sprayer}")
            }

            override fun onCancelled(error: DatabaseError) {
                _stateControl.update {
                    it.copy(
                        Fan =  0,
                        Heater =  0,
                        Sprayer =  0
                    )
                }
            }
        })
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
    init {
        fetchMonitoringData()
        fetchControlData()
    }
}