package com.minux.reminder.feature_reminder.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.reminder.core.util.Constants.ERROR_UNKNOWN
import com.minux.reminder.core.util.Constants.TAG_APP
import com.minux.reminder.core.util.Constants.TYPE_VIEW_EDIT
import com.minux.reminder.core.util.Constants.TYPE_VIEW_NEW
import com.minux.reminder.core.util.Resource
import com.minux.reminder.feature_reminder.domain.model.Reminder
import com.minux.reminder.feature_reminder.domain.use_case.ReminderUseCase
import com.minux.reminder.feature_reminder.presentation.reminder.ReminderUiState
import com.minux.reminder.feature_reminder.presentation.reminders.RemindersUiState
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

    private val _reminderUiState = MutableLiveData(ReminderUiState())
    val reminderUiState: LiveData<ReminderUiState> get() = _reminderUiState

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        getReminders()
    }

    fun changeReminderViewType(reminder: Reminder?) {
        _reminderUiState.value = reminderUiState.value?.copy(
            viewType = if(reminder == null) TYPE_VIEW_NEW else TYPE_VIEW_EDIT,
        )
    }

    fun insertReminder(hour: Int, minute: Int) {
        viewModelScope.launch {

            usecase.insertUseCase(reminderUiState.value?.name?.value ?: "", hour, minute)
                .onEach { result ->
                    when(result) {
                        is Resource.Loading -> {
                            _isLoading.value = true
                        }
                        is Resource.Success -> {
                            _isLoading.value = false
                        }
                        is Resource.Error -> {
                            _isLoading.value = false
                        }
                    }
                }.launchIn(this)
        }
    }

    fun getReminders() {
        viewModelScope.launch {
            usecase.getRemindersUseCase().onEach { result ->
                when(result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {


                        _remindersUiState.value = remindersUiState.value?.copy(
                            reminders = result.data ?: emptyList(),
                            error = ""
                        )

                        Log.d(TAG_APP, "RMINDERS: ${result.data.toString()}")
                    }
                    is Resource.Error -> {


                        _remindersUiState.value = remindersUiState.value?.copy(
                            error = result.message ?: ERROR_UNKNOWN,
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}