package com.arcadia.arciegg.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcadia.arciegg.repository.SensorListRepository
import com.arcadia.arciegg.uiState.SensorData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.roundToInt

class LineChartViewModel : ViewModel() {
    private val repository = SensorListRepository()
    val monitoringData : LiveData<List<SensorData>> = repository.fetchSensorDataList()

    init {
        fetchSensorData()
        //scheduleDataDeletion()
    }
    private fun fetchSensorData() {
        repository.fetchSensorDataList()
    }
    private fun scheduleDataDeletion(){
        viewModelScope.launch {
            while (true) {
                val calender = Calendar.getInstance()
                val currentDayOfWeek = calender.get(Calendar.DAY_OF_WEEK)
                val dayUntilSunday = (Calendar.SUNDAY - currentDayOfWeek + 7) % 7
                val millisecondsUntilSunday = dayUntilSunday * 24 * 60 * 60 * 1000L

                if (dayUntilSunday == 0) {
                    repository.deleteSensorData()
                    delay(7*24*60*60*1000L)
                } else{
                    delay(millisecondsUntilSunday)
                    repository.deleteSensorData()
                    delay(7*24*60*60*1000L)
                }
            }
        }
    }

    fun roundup(formatted: Double?): Double {
        val roundup = formatted!! * 100.0
        return roundup.roundToInt() / 100.0
    }
    fun formatDateTime (unixTimestamp: Long): String {
        val date = Date(unixTimestamp * 1000L)
        val format = SimpleDateFormat("dd-MM-yyyy HH:mm",Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC")
        return format.format(date)
    }
}