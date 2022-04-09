package com.minux.reminder.feature_reminder.domain.repository

import com.minux.reminder.core.util.Resource
import com.minux.reminder.feature_reminder.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    fun getReminders(): Flow<Resource<List<Reminder>>>
}