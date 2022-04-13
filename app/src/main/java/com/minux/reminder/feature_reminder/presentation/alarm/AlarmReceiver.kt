package com.minux.reminder.feature_reminder.presentation.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.minux.reminder.core.util.Constants
import com.minux.reminder.core.util.Constants.EXTRA_ALARM_ON_OFF
import com.minux.reminder.core.util.Constants.EXTRA_REMINDER_ID
import com.minux.reminder.core.util.Constants.TAG_APP
import com.minux.reminder.feature_reminder.presentation.main.MainActivity

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Service로 리마인더 아이템의 벨소리 실행
        // 알림 상태값 ON -> AlarmFragment 로 Navigate
        // AlarmFragment 에서 'Dismiss' 버튼을 누르면 알람 OFF
        Log.d(TAG_APP, "Received reminder [${intent?.getIntExtra(EXTRA_REMINDER_ID, -1) ?: -1}]")

        val reminderId = intent?.getIntExtra(EXTRA_REMINDER_ID, -1) ?: return
        if(reminderId < 1) return

        val newIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(EXTRA_REMINDER_ID, reminderId)
        }

        val alarmIntent = Intent(context, AlarmService::class.java).apply {
            putExtra(EXTRA_ALARM_ON_OFF, true)
        }
        context?.startService(alarmIntent)
        context?.startActivity(newIntent)
    }
}