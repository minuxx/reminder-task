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
        if(name.isBlank() || name.length > 20) {
            return flow { emit(Resource.Error(
                message = if (name.isBlank()) "리마인더 이름을 입력해주세요." else "리마인더 이름은 20자 이하로 입력해주세요."
            )) }
        }

        val m = if(minute < 10) "0$minute" else "$minute"

        val time = when {
            hour < 12 -> {
                "${if(hour < 10) "0$hour" else "$hour"}:$m AM"
            }
            hour == 12 -> {
                "$hour:$m PM"
            }
            else -> {
                "${if(hour >= 22) "${hour - 12}" else "0${hour - 12}"}:$m PM"
            }
        }

        val reminder = Reminder(name = name, time = time)

        return repository.insertReminder(reminder)
    }
}