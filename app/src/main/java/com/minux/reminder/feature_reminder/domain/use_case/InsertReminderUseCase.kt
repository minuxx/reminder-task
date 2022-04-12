package com.minux.reminder.feature_reminder.domain.use_case

import com.minux.reminder.core.util.Resource
import com.minux.reminder.feature_reminder.domain.model.Reminder
import com.minux.reminder.feature_reminder.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertReminderUseCase(
    private val repository: ReminderRepository
) {
    operator fun invoke(name: String, hour: Int, minute: Int): Flow<Resource<Unit>> {
        if(name.isBlank() || hour < 0 || minute < 0) {
            return flow { emit(Resource.Error(message = "VALIDATION_ERROR")) }
        }

        val time = if(hour < 12) {
            "0${hour}:${if(minute < 10) "0$minute" else "$minute"} AM"
        } else {
            if(hour >= 22) "$hour:" else "0${hour - 12}:${if(minute < 10) "0$minute" else "$minute"} PM"
        }

        val reminder = Reminder(name = name, time = time)

        return repository.insertReminder(reminder)
    }
}