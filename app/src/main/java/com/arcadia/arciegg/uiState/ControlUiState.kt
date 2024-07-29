package com.arcadia.arciegg.uiState

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ControlUiState(
    val Fan: Int? = 0,
    val Heater: Int? = 0,
    val Sprayer: Int? = 0
)
