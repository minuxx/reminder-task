package com.minux.reminder.feature_reminder.presentation.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.minux.reminder.core.util.Constants
import com.minux.reminder.core.util.Constants.TAG_APP
import com.minux.reminder.feature_reminder.presentation.main.MainActivity

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Service로 리마인더 아이템의 벨소리 실행
        // 알림 상태값 ON -> AlarmFragment 로 Navigate
        // AlarmFragment 에서 'Dismiss' 버튼을 누르면 알람 OFF
        Log.d(TAG_APP, "Received reminder [${intent?.getIntExtra(Constants.EXTRA_REMINDER_ID, -1) ?: -1}]")

        val newIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(Constants.EXTRA_REMINDER_ID, intent?.getIntExtra(Constants.EXTRA_REMINDER_ID, -1) ?: -1)
        }

        context?.startActivity(newIntent)
    }
}