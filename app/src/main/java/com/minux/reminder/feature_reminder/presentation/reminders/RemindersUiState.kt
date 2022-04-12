package com.minux.reminder.feature_reminder.presentation.reminders

import com.minux.reminder.feature_reminder.domain.model.Reminder

data class RemindersUiState(
    val reminders: List<Reminder> = emptyList(),
    val error: String = ""
)
