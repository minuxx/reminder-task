package com.minux.reminder.feature_reminder.domain.model

data class Reminder(
    val id: Int = -1,
    val name: String,
    val time: String,
    val isActivated: Boolean = false,
)
