package com.dokari4.core.util

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateConverter {

    @TypeConverter
    fun setDateAndTimeToLong(date: String, time: String): Long {
//        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
//        val parsedDateTime = formatter.parse("$date $time")
//        val instant = Instant.from(parsedDateTime)
//        val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
//        return zonedDateTime.toInstant().toEpochMilli()

//        val local = LocalDate.parse("$date $time", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
//        return local.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond

        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val convertedDate = formatter.parse("$date $time")
        return convertedDate!!.time
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