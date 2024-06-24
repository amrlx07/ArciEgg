package com.arcadia.arciegg.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arcadia.arciegg.uiState.SensorData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SensorListRepository {
    //path database for fetch data
    private val databaseList = FirebaseDatabase.getInstance().reference.child("ARCI_EGG").child("Monitoring").child("data")
    private val databaseMonitoring = FirebaseDatabase.getInstance().reference.child("ARCI_EGG").child("Monitoring")
    private val databaseControl = FirebaseDatabase.getInstance().reference.child("ARCI_EGG").child("Control")
    private val databaseSensorCheck = FirebaseDatabase.getInstance().reference.child("ARCI_EGG").child("SensorCheck")
    private val databaseSetpoint = FirebaseDatabase.getInstance().reference.child("ARCI_EGG").child("SetPoint")

    fun fetchSensorDataList(): LiveData<List<SensorData>> {
        val liveData = MutableLiveData<List<SensorData>>()
        databaseList.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("Firebase", "Successfully to fetch data.")
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
                Log.e("Firebase", "Failed to fetch data.", error.toException())
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