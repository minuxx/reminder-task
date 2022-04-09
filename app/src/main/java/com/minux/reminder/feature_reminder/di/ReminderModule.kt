package com.minux.reminder.feature_reminder.di

import com.minux.reminder.feature_reminder.domain.repository.ReminderRepository
import com.minux.reminder.feature_reminder.domain.use_case.GetRemindersUseCase
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
    fun provideGetRemindersUseCase(repository: ReminderRepository): GetRemindersUseCase {
        return GetRemindersUseCase(repository)
    }
}