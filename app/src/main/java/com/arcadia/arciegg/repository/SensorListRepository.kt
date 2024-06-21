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
    private val database = FirebaseDatabase.getInstance().reference.child("ARCI_EGG").child("Monitoring").child("data")

    fun fetchSensorData(): LiveData<List<SensorData>> {
        val liveData = MutableLiveData<List<SensorData>>()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
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
        database.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Data deleted successfully.")
            } else {
                Log.e("Firebase", "Failed to delete data.", task.exception)
            }
        }
    }
}