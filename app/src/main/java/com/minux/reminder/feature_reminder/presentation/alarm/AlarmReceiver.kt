package com.minux.reminder.feature_reminder.presentation.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.minux.reminder.core.util.Constants.TAG_APP

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG_APP, "Received: {intent.toString()}")
        // Service로 리마인더 아이템의 벨소리 실행
        // 알림 상태값 ON -> AlarmFragment 로 Navigate
        // AlarmFragment 에서 'Dismiss' 버튼을 누르면 알람 OFF
    }
}