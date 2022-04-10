package com.minux.reminder.feature_reminder.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.reminder.core.util.Constants.ERROR_UNKNOWN
import com.minux.reminder.core.util.Resource
import com.minux.reminder.feature_reminder.domain.model.Reminder
import com.minux.reminder.feature_reminder.domain.use_case.ReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val usecase: ReminderUseCase
): ViewModel() {
    private val _remindersUiState = MutableLiveData(RemindersUiState())
    val remindersUiState: LiveData<RemindersUiState> get() = _remindersUiState

    init {
        getReminders()
    }

    fun insertReminder(name: String, time: String) {
        viewModelScope.launch {
            val reminder = Reminder(name = name, time = time)

            usecase.insertUseCase(reminder).onEach { result ->
                when(result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {

                    }
                    is Resource.Error -> {

                    }
                }
            }.launchIn(this)
        }
    }

    private fun getReminders() {
        viewModelScope.launch {
            usecase.getRemindersUseCase().onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _remindersUiState.value = remindersUiState.value?.copy(
                            isLoading = true,
                            error = ""
                        )
                    }
                    is Resource.Success -> {
                        _remindersUiState.value = remindersUiState.value?.copy(
                            reminders = result.data ?: emptyList(),
                            isLoading = false,
                            error = ""
                        )
                    }
                    is Resource.Error -> {
                        _remindersUiState.value = remindersUiState.value?.copy(
                            error = result.message ?: ERROR_UNKNOWN,
                            isLoading = false,
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun insert10RemindersForTest() {
        viewModelScope.launch {
            for (i in 1..10) {
                insertReminder("${i}번째 리마인더", "${i}:00 AM")
            }
        }
    }
}