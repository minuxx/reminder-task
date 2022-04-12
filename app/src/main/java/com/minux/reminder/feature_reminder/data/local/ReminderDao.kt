package com.minux.reminder.feature_reminder.data.local

import androidx.room.*
import com.minux.reminder.feature_reminder.data.local.entity.ReminderEntity

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminderentity")
    suspend fun getReminders(): List<ReminderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderEntity)

    @Update
    suspend fun updateReminder(reminder: ReminderEntity)
}