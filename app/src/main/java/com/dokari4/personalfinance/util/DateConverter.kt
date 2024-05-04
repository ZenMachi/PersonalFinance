package com.dokari4.personalfinance.util

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object DateConverter {

    @TypeConverter
    fun setDateAndTimeToLong(date: String, time: String): Long {
        val local = LocalDate.parse("$date $time", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        return local.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond
    }

    fun setTimeToHourAndMinutes(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        return format.format(date)
    }

    fun setTimeToDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(date)
    }

    fun formatDate(date: Date): String {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(date)
    }

    fun formatTime(date: Date): String {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        return format.format(date)
    }
}