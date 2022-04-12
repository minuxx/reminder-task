package com.minux.reminder.core.util

import android.util.Log
import com.minux.reminder.core.util.Constants.TAG_APP

object TimeFormatUtil {
    fun getHourFromTime(time: String): Int {
        val s = time.split(":")

        val hour = s[0].toInt()
        val amPm = s[1].split(" ")[1]

        return if (amPm == "AM" || hour == 12) {
            hour
        } else {
            hour + 12
        }
    }

    fun getMinuteFromTime(time: String): Int {
        val s = time.split(":")
        val minute = s[1].split(" ")[0].toInt()

        return minute
    }

    fun getTimeFormat(hour: Int, minute: Int): String {
        val m = if(minute < 10) "0$minute" else "$minute"

        return when {
            hour < 12 -> {
                "${if(hour < 10) "0$hour" else "$hour"}:$m AM"
            }
            hour == 12 -> {
                "$hour:$m PM"
            }
            else -> {
                "${if(hour >= 22) "${hour - 12}" else "0${hour - 12}"}:$m PM"
            }
        }
    }
}