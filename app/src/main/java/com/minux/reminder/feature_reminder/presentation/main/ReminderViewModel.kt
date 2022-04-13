package com.minux.reminder.feature_reminder.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.reminder.core.util.Constants.ERROR_MSG_GET_REMINDERS
import com.minux.reminder.core.util.Constants.ERROR_MSG_SET_REMINDER
import com.minux.reminder.core.util.Constants.EVENT_MOVE_REMINDERS
import com.minux.reminder.core.util.Constants.TAG_APP
import com.minux.reminder.core.util.Constants.TYPE_VIEW_EDIT
import com.minux.reminder.core.util.Constants.TYPE_VIEW_NEW
import com.minux.reminder.core.util.Resource
import com.minux.reminder.core.util.TimeFormatUtil
import com.minux.reminder.core.util.UiEvent
import com.minux.reminder.feature_reminder.domain.model.Reminder
import com.minux.reminder.feature_reminder.domain.use_case.ReminderUseCase
import com.minux.reminder.feature_reminder.presentation.alarm.AlarmUiState
import com.minux.reminder.feature_reminder.presentation.reminder.ReminderUiState
import com.minux.reminder.feature_reminder.presentation.reminders.RemindersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val usecase: ReminderUseCase
) : ViewModel() {
    private val _remindersUiState = MutableLiveData(RemindersUiState())
    val remindersUiState: LiveData<RemindersUiState> get() = _remindersUiState

    private val _reminderUiState = MutableLiveData(ReminderUiState())
    val reminderUiState: LiveData<ReminderUiState> get() = _reminderUiState

    private val _alarmUiState = MutableLiveData(AlarmUiState())
    val alarmUiState: LiveData<AlarmUiState> get() = _alarmUiState

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getReminders()
    }

    fun changeReminderViewType(reminder: Reminder?) {
        _reminderUiState.value = reminderUiState.value?.copy(
            viewType = if (reminder == null) TYPE_VIEW_NEW else TYPE_VIEW_EDIT,
            id = if (reminder == null) -1 else reminder.id,
            name = if (reminder == null) MutableLiveData("") else MutableLiveData(reminder.name),
            hour = if (reminder == null) 0 else TimeFormatUtil.getHourFromTime(reminder.time),
            minute = if (reminder == null) 0 else TimeFormatUtil.getMinuteFromTime(reminder.time),
            isActivated = if (reminder == null) true else reminder.isActivated
        )
    }

    private fun getReminders() {
        viewModelScope.launch {
            usecase.getRemindersUseCase().onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                    is Resource.Success -> {
                        _isLoading.value = false
                        _remindersUiState.value = remindersUiState.value?.copy(
                            reminders = result.data ?: emptyList(),
                            error = ""
                        )

                        _eventFlow.emit(
                            UiEvent.GetRemindersSuccess()
                        )
                    }
                    is Resource.Error -> {
                        _isLoading.value = false
                        _eventFlow.emit(
                            UiEvent.ShowToast(result.message ?: ERROR_MSG_GET_REMINDERS)
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun insertReminder(hour: Int, minute: Int) {
        viewModelScope.launch {
            usecase.insertUseCase(reminderUiState.value?.name?.value ?: "", hour, minute)
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _isLoading.value = true
                        }
                        is Resource.Success -> {
                            _isLoading.value = false
                            // 알람을 설정하라는 시그널
                            _eventFlow.emit(
                                UiEvent.SetReminder(result.data ?: -1 , hour, minute)
                            )

                            _eventFlow.emit(
                                UiEvent.Navigate(EVENT_MOVE_REMINDERS)
                            )

                            getReminders()
                        }
                        is Resource.Error -> {
                            _isLoading.value = false
                            _eventFlow.emit(
                                UiEvent.ShowToast(result.message ?: ERROR_MSG_SET_REMINDER)
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            usecase.updateReminderUseCase(reminder)
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _isLoading.value = true
                        }
                        is Resource.Success -> {
                            _isLoading.value = false

                            // isActivated에 의해 알람/해제하라는 시그널
                            if (reminder.isActivated) {
                                // 새로운 시간대의 알람 설정 명령
                                _eventFlow.emit(
                                    UiEvent.SetReminder(
                                        reminder.id,
                                        TimeFormatUtil.getHourFromTime(reminder.time),
                                        TimeFormatUtil.getMinuteFromTime(reminder.time)
                                    )
                                )
                            } else {
                                // 알람 비활성화 체크 시 알람 해지 명령
                                _eventFlow.emit(
                                    UiEvent.UnsetReminder(reminder.id)
                                )
                            }

                            getReminders()

                            _eventFlow.emit(
                                UiEvent.Navigate(EVENT_MOVE_REMINDERS)
                            )
                        }
                        is Resource.Error -> {
                            _isLoading.value = false
                            _eventFlow.emit(
                                UiEvent.ShowToast(result.message ?: ERROR_MSG_SET_REMINDER)
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    fun setAlarmUiState(reminderId: Int) {
        val reminder = remindersUiState.value?.reminders?.find { it.id == reminderId }

        Log.d(TAG_APP, "Set AlarmUiState reminderId: $reminderId")

        _alarmUiState.value = alarmUiState.value?.copy(
            reminder = reminder
        )
    }
}