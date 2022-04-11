package com.minux.reminder.feature_reminder.presentation.main

import com.minux.reminder.core.util.Constants.TYPE_VIEW_NEW
import com.minux.reminder.feature_reminder.domain.model.Reminder

data class ReminderUiState(
    val viewType: String = TYPE_VIEW_NEW,
    val reminder: Reminder? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
