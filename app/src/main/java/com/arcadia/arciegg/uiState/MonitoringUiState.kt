package com.arcadia.arciegg.uiState

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class MonitoringUiState(
    val Suhu: Double? = 0.0,
    val Kelembapan: Double? = 0.0
)
