package com.minux.reminder.feature_reminder.presentation.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.minux.reminder.R
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

        initUI()
        observeEventFlow()
    }

    private fun initUI() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun observeEventFlow() {
        lifecycleScope.launch {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UiEvent.SetReminder -> {
                        if(event.id > 0) {
                            setReminder(event.id, event.hour, event.minute)
                        }
                    }
                    is UiEvent.UnsetReminder -> {
                        event.alarmIntent?.let{
                            unsetReminder(it)
                        }
                    }
                    is UiEvent.Navigate -> {

                    }
                    is UiEvent.ShowToast -> {

                    }
                    else -> {}
                }
            }
        }
    }

    private fun setReminder(id: Int, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        viewModel.addAlarmIntent(id, alarmIntent)

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        Log.d(TAG_APP, "Set Alarm Time: ${calendar.time}")
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
    }

    private fun unsetReminder(alarmIntent: PendingIntent) {
        Log.d(TAG_APP, "Unset Alarm")
        alarmManager.cancel(alarmIntent)
    }
}