package com.minux.reminder.feature_reminder.presentation

import androidx.lifecycle.ViewModel
import com.minux.reminder.feature_reminder.domain.use_case.ReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val usecase: ReminderUseCase
): ViewModel() {

}