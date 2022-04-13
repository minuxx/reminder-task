package com.minux.reminder.core.util

import android.app.PendingIntent

sealed class UiEvent {
    data class ShowToast(val message: String): UiEvent()
    data class Navigate(val flag: String): UiEvent()
    data class SetReminder(val id: Int, val hour: Int, val minute: Int): UiEvent()
    class UnsetReminder(val reminderId: Int): UiEvent()
    class GetRemindersSuccess(): UiEvent()
}