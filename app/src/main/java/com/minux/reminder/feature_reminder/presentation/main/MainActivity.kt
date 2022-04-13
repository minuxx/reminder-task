package com.minux.reminder.feature_reminder.presentation.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.minux.reminder.R
import com.minux.reminder.core.util.Constants.EXTRA_REMINDER_ID
import com.minux.reminder.core.util.Constants.TAG_APP
import com.minux.reminder.core.util.UiEvent
import com.minux.reminder.databinding.ActivityMainBinding
import com.minux.reminder.feature_reminder.presentation.alarm.AlarmReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: ReminderViewModel by viewModels()

    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        initUI()
        observeEventFlow()
    }

    private fun initUI() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun observeEventFlow() {
        lifecycleScope.launch {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UiEvent.SetReminder -> {
                        if (event.id > 0) {
                            setReminder(event.id, event.hour, event.minute)
                        }
                    }
                    is UiEvent.UnsetReminder -> {
                        unsetReminder(event.reminderId)
                    }
                    is UiEvent.GetRemindersSuccess -> {
                        val reminderId = intent.getIntExtra(EXTRA_REMINDER_ID, -1)

//                        Log.d(TAG_APP, "MAIN EXTRA-REMINDER-ID: $reminderId")

                        if (reminderId > 0) {
                            navController.navigate(R.id.alarmFragment)
                            viewModel.setAlarmUiState(reminderId)
                            intent.removeExtra(EXTRA_REMINDER_ID)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setReminder(reminderId: Int, hour: Int, minute: Int) {
        if (reminderId < 1) return

        val calendar = Calendar.getInstance()

        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_REMINDER_ID, reminderId)

        val alarmIntent =
            PendingIntent.getBroadcast(this, reminderId, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        Log.d(TAG_APP, "Set Reminder [$reminderId], Time: ${calendar.time}")
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
    }

    private fun unsetReminder(reminderId: Int) {
        if (reminderId < 1) return

        Log.d(TAG_APP, "Unset Reminder [$reminderId]")

        val intent = Intent(this, AlarmReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(this, reminderId, intent, 0)

        alarmManager.cancel(alarmIntent)
    }
}