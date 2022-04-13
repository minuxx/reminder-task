package com.minux.reminder.feature_reminder.domain.use_case

import com.minux.reminder.core.util.Resource
import com.minux.reminder.core.util.TimeFormatUtil
import com.minux.reminder.feature_reminder.domain.model.Reminder
import com.minux.reminder.feature_reminder.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertReminderUseCase(
    private val repository: ReminderRepository
) {
    operator fun invoke(name: String, hour: Int, minute: Int): Flow<Resource<Int>> {
        if(name.isBlank() || name.length > 20) {
            return flow { emit(Resource.Error(
                message = if (name.isBlank()) "리마인더 이름을 입력해주세요." else "리마인더 이름은 20자 이하로 입력해주세요."
            )) }
        }

        val reminder = Reminder(name = name, time = TimeFormatUtil.getTimeFormat(hour, minute))

        return repository.insertReminder(reminder)
    }
}