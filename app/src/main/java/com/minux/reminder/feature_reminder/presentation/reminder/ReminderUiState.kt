package com.minux.reminder.feature_reminder.presentation.reminder

import androidx.lifecycle.MutableLiveData
import com.minux.reminder.core.util.Constants.TYPE_VIEW_NEW

data class ReminderUiState(
    val viewType: String = TYPE_VIEW_NEW,
    val error: String = "",
    val id: Int = -1,
    val name: MutableLiveData<String> = MutableLiveData(""),
    val hour: Int = 0,
    val minute: Int = 0,
    val isActivated: Boolean = true,
)
