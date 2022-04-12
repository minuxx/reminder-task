package com.minux.reminder.feature_reminder.domain.use_case

import com.minux.reminder.core.util.Resource
import com.minux.reminder.feature_reminder.domain.model.Reminder
import com.minux.reminder.feature_reminder.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow

class GetRemindersUseCase(
    private val repository: ReminderRepository
) {
    operator fun invoke(): Flow<Resource<List<Reminder>>> {
        return repository.getReminders()
    }
}