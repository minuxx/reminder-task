package com.minux.reminder.feature_reminder.presentation.reminder

import androidx.lifecycle.MutableLiveData
import com.minux.reminder.core.util.Constants.TYPE_VIEW_NEW

data class ReminderUiState(
    val viewType: String = TYPE_VIEW_NEW,
    var error: String = "",
    val name: MutableLiveData<String> = MutableLiveData("")
)
