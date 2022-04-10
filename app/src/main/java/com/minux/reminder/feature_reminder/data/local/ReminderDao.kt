package com.minux.reminder.feature_reminder.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.minux.reminder.feature_reminder.data.local.entity.ReminderEntity

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderEntity)

    @Query("SELECT * FROM reminderentity")
    suspend fun getReminders(): List<ReminderEntity>
}