package com.minux.reminder.feature_reminder.di

import android.app.Application
import androidx.room.Room
import com.minux.reminder.feature_reminder.data.local.ReminderDatabase
import com.minux.reminder.feature_reminder.data.repository.ReminderRepositoryImpl
import com.minux.reminder.feature_reminder.domain.model.Reminder
import com.minux.reminder.feature_reminder.domain.repository.ReminderRepository
import com.minux.reminder.feature_reminder.domain.use_case.GetRemindersUseCase
import com.minux.reminder.feature_reminder.domain.use_case.InsertReminderUseCase
import com.minux.reminder.feature_reminder.domain.use_case.ReminderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReminderModule {
    @Provides
    @Singleton
    fun provideReminderDatabase(app: Application): ReminderDatabase {
        return Room.databaseBuilder(
            app,
            ReminderDatabase::class.java, "reminder-db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideReminderRepository(db: ReminderDatabase): ReminderRepository {
        return ReminderRepositoryImpl(db.reminderDao)
    }

    @Provides
    @Singleton
    fun provideReminderUseCases(repository: ReminderRepository): ReminderUseCase {
        return ReminderUseCase(
            insertUseCase = InsertReminderUseCase(repository),
            getRemindersUseCase = GetRemindersUseCase(repository)
        )
    }
}