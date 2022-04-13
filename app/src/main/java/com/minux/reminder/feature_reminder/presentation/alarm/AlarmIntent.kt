package com.minux.reminder.feature_reminder.presentation.alarm

import android.app.PendingIntent

data class AlarmIntent(
    val reminderId: Int = -1,
    val intent: PendingIntent? = null
)
