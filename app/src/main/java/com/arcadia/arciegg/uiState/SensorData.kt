package com.arcadia.arciegg.uiState

data class SensorData(
    val DateTime: Long = 0L,
    val Kelembapan: Double = 0.0,
    val Suhu: Double = 0.0,
    val SetPointKelembapan: Double = 0.0,
    val SetPointSuhu: Double = 0.0
)
