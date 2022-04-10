package com.minux.reminder.feature_reminder.domain.use_case

class ReminderUseCase(
    val insertUseCase: InsertReminderUseCase,
    val getRemindersUseCase: GetRemindersUseCase
)