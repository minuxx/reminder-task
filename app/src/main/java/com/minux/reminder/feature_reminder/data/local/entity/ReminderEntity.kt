package com.minux.reminder.feature_reminder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.minux.reminder.feature_reminder.domain.model.Reminder

@Entity
data class ReminderEntity(
    val name: String,
    val time: String,
    val isActivated: Boolean = true,
    @PrimaryKey val id: Int? = null
) {
    fun toReminder(): Reminder {
        return Reminder(
            id = id ?: -1,
            name = name,
            time = time,
            isActivated = isActivated
        )
    }
}
