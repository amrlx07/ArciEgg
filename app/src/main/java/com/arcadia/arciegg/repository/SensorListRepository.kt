package com.arcadia.arciegg.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arcadia.arciegg.uiState.ControlUiState
import com.arcadia.arciegg.uiState.MonitoringUiState
import com.arcadia.arciegg.uiState.SensorCheckUiState
import com.arcadia.arciegg.uiState.SensorData
import com.arcadia.arciegg.uiState.SetPointUiState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow

class SensorListRepository {
    //path database for fetch data
    private val databaseList = FirebaseDatabase.getInstance().reference.child("ARCI_EGG").child("Monitoring").child("data")
    val databaseMonitoring = FirebaseDatabase.getInstance().reference.child("ARCI_EGG").child("Monitoring")
    val databaseControl = FirebaseDatabase.getInstance().reference.child("ARCI_EGG").child("Control")
    val databaseSensorCheck = FirebaseDatabase.getInstance().reference.child("ARCI_EGG").child("SensorCheck")
    val databaseSetpoint = FirebaseDatabase.getInstance().reference.child("ARCI_EGG").child("Setpoint")

//    fun databaseControl (): Flow<ControlUiState?> {
//        return callbackFlow {
//            val listener = databaseControl.addValueEventListener(object : ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    Log.d("FirebaseControl", "Successfully to fetch data")
//                    val getControl = snapshot.getValue<ControlUiState>()
//                    trySend(getControl)// Kirim data ke aliran
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Log.e("FirebaseControl", "Failed to fetch data.", error.toException())
//                    trySend(null) // Atau kirim indikasi errora
//                    close(error.toException()) // Tutup aliran jika terjadi error
//                }
//
//            })
//            awaitClose{
//                databaseControl.removeEventListener(listener)
//            }
//        }
//    }
    fun fetchSensorCheck(): Flow<SensorCheckUiState?> {
        return callbackFlow {
            val listener = databaseSensorCheck.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("FirebaseSensorCheck", "Successfully to fetch data.")
                    val getSensorCheck = snapshot.getValue<SensorCheckUiState>()
                    trySend(getSensorCheck) // Kirim data ke aliran
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseSensorCheck", "Failed to fetch data.", error.toException())
                    trySend(null) // Atau kirim indikasi error
                    close(error.toException()) // Tutup aliran jika terjadi error
                }
            })
            awaitClose{
                databaseSensorCheck.removeEventListener(listener)
            }
        }
    }

    fun fetchSetpoint(): Flow<SetPointUiState?> {
        return callbackFlow {
            val listener = databaseSetpoint.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("FirebaseSetpoint", "Successfully to fetch data.")
                    val getDataSetpoint = snapshot.getValue<SetPointUiState>()
                    trySend(getDataSetpoint) // Kirim data ke aliran
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseSetPoint", "Failed to fetch data.", error.toException())
                    trySend(null) // Atau kirim indikasi error
                    close(error.toException()) // Tutup aliran jika terjadi error
                }
            })
            awaitClose{
                databaseSetpoint.removeEventListener(listener)
            }
        }
    }

//    fun fetchMonitoringData(): Flow<MonitoringUiState?> {
//        return callbackFlow {
//            val listener = databaseMonitoring.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    Log.d("FirebaseMonitoring", "Successfully to fetch data.")
//                    val getDataMonitoring = snapshot.getValue<MonitoringUiState>()
//                    trySend(getDataMonitoring) // Kirim data ke aliran
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Log.e("FirebaseMonitoring", "Failed to fetch data.", error.toException())
//                    trySend(null) // Atau kirim indikasi error
//                    close(error.toException()) // Tutup aliran jika terjadi error
//                }
//            })
//
//            awaitClose {
//                databaseMonitoring.removeEventListener(listener) // Hapus listener ketika aliran ditutup
//            }
//        }
//    }
    fun fetchSensorDataList(): LiveData<List<SensorData>> {
        val liveData = MutableLiveData<List<SensorData>>()
        databaseList.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("FirebaseList", "Successfully to fetch data.")
                val sensorList = mutableListOf<SensorData>()
                snapshot.children.forEach { dataSnapshot ->
                    val sensorDetail = dataSnapshot.getValue(SensorData::class.java)
                    sensorDetail?.let {
                        sensorList.add(it)
                    }
                    liveData.value = sensorList
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseList", "Failed to fetch data.", error.toException())
                // Handle possible errors.
            }
        })
        return liveData
    }

    fun deleteSensorData() {
        databaseList.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Data deleted successfully.")
            } else {
                Log.e("Firebase", "Failed to delete data.", task.exception)
            }
        }
    }
}