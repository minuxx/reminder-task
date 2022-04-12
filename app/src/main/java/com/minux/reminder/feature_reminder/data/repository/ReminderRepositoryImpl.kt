package com.minux.reminder.feature_reminder.data.repository

import android.database.sqlite.SQLiteException
import android.util.Log
import com.minux.reminder.core.util.Constants.ERROR_MSG_GET_REMINDERS
import com.minux.reminder.core.util.Constants.ERROR_MSG_SET_REMINDER
import com.minux.reminder.core.util.Constants.ERROR_SQLITE
import com.minux.reminder.core.util.Constants.ERROR_UNKNOWN
import com.minux.reminder.core.util.Constants.TAG_APP
import com.minux.reminder.core.util.Resource
import com.minux.reminder.core.util.TimeFormatUtil
import com.minux.reminder.feature_reminder.data.local.ReminderDao
import com.minux.reminder.feature_reminder.data.local.entity.ReminderEntity
import com.minux.reminder.feature_reminder.domain.model.Reminder
import com.minux.reminder.feature_reminder.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class ReminderRepositoryImpl(
    private val dao: ReminderDao
) : ReminderRepository {
    override fun getReminders(): Flow<Resource<List<Reminder>>> = flow {
        emit(Resource.Loading())

        try {
            val reminders = dao.getReminders()
                .map { it.toReminder() }
                .sortedWith(compareBy({ TimeFormatUtil.getHourFromTime(it.time) }, {TimeFormatUtil.getMinuteFromTime(it.time) }))
            emit(Resource.Success(
                data = reminders
            ))
        } catch (e: SQLiteException) {
            Log.e(TAG_APP, e.localizedMessage ?: ERROR_SQLITE)

            emit(Resource.Error(
                message = ERROR_MSG_GET_REMINDERS
            ))
        } catch (e: Exception) {
            Log.e(TAG_APP, e.localizedMessage ?: ERROR_UNKNOWN)

            emit(Resource.Error(
                message = ERROR_MSG_GET_REMINDERS
            ))
        }
    }

    override fun insertReminder(reminder: Reminder): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        try {
            dao.insertReminder(reminder = ReminderEntity(
                name = reminder.name,
                time = reminder.time,
            ))
            emit(Resource.Success())
        } catch (e: SQLiteException) {
            Log.e(TAG_APP, e.localizedMessage ?: ERROR_SQLITE)

            emit(Resource.Error(
                message = ERROR_MSG_SET_REMINDER
            ))
        } catch (e: Exception) {
            Log.e(TAG_APP, e.localizedMessage ?: ERROR_UNKNOWN)

            emit(Resource.Error(
                message = ERROR_MSG_SET_REMINDER
            ))
        }
    }

    override fun updateReminder(reminder: Reminder): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        try {
            dao.updateReminder(reminder = ReminderEntity(
                id = reminder.id,
                name = reminder.name,
                time = reminder.time,
                isActivated = reminder.isActivated
            ))
            emit(Resource.Success())
        } catch (e: SQLiteException) {
            Log.e(TAG_APP, e.localizedMessage ?: ERROR_SQLITE)

            emit(Resource.Error(
                message = ERROR_MSG_SET_REMINDER
            ))
        } catch (e: Exception) {
            Log.e(TAG_APP, e.localizedMessage ?: ERROR_UNKNOWN)

            emit(Resource.Error(
                message = ERROR_MSG_SET_REMINDER
            ))
        }
    }
}