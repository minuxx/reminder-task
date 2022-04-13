package com.minux.reminder.feature_reminder.presentation.alarm

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import com.minux.reminder.R
import com.minux.reminder.core.util.Constants.EXTRA_ALARM_ON_OFF
import com.minux.reminder.core.util.Constants.TAG_APP

class AlarmService: Service() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val isAlarmOn = intent?.getBooleanExtra(EXTRA_ALARM_ON_OFF, false) ?: false

        if(isAlarmOn) {
            mediaPlayer = MediaPlayer.create(this, R.raw.alarm_music)
            mediaPlayer.start()

            Log.d(TAG_APP, "Alarm Media ON")
        } else {
            mediaPlayer.stop()
            mediaPlayer.release()

            Log.d(TAG_APP, "Alarm Media  OFF")
        }

        return START_NOT_STICKY
    }
}