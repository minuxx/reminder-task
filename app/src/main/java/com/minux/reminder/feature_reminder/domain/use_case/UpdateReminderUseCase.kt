package com.minux.reminder.feature_reminder.domain.use_case

import com.minux.reminder.core.util.Resource
import com.minux.reminder.feature_reminder.domain.model.Reminder
import com.minux.reminder.feature_reminder.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow

class UpdateReminderUseCase(
    private val repository: ReminderRepository
) {
    operator fun invoke(reminder: Reminder): Flow<Resource<Unit>> {
        return repository.updateReminder(reminder)
    }
}