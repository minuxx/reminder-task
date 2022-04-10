package com.minux.reminder.feature_reminder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.minux.reminder.feature_reminder.data.local.entity.ReminderEntity

@Database(
    entities = [ReminderEntity::class],
    version = 1
)
abstract class ReminderDatabase: RoomDatabase() {
    abstract val reminderDao: ReminderDao
}