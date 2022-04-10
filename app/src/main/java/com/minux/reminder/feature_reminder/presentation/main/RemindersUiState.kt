package com.minux.reminder.feature_reminder.presentation.main

import com.minux.reminder.feature_reminder.domain.model.Reminder

data class RemindersUiState(
    val reminders: List<Reminder> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
