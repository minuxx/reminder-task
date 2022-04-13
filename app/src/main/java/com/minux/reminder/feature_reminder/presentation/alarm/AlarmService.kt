package com.minux.reminder.feature_reminder.presentation.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.minux.reminder.R
import com.minux.reminder.core.util.Constants.EXTRA_ALARM_ON_OFF
import com.minux.reminder.core.util.Constants.EXTRA_NOTIFICATION_ID
import com.minux.reminder.core.util.Constants.NOTIFICATION_CHANNEL_ID
import com.minux.reminder.core.util.Constants.NOTIFICATION_CHANNEL_NAME
import com.minux.reminder.core.util.Constants.TAG_APP

class AlarmService: Service() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val isAlarmOn = intent?.getBooleanExtra(EXTRA_ALARM_ON_OFF, false) ?: false
        val notificationId = intent?.getIntExtra(EXTRA_NOTIFICATION_ID, -1) ?: 0

        if(isAlarmOn) {
            if (Build.VERSION.SDK_INT >= 26) {
                val channel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }

            val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_reminder)
                .setContentTitle("리마인더 알람")
                .setContentText("리마인더가 실행중이에요.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL).build()

            startForeground(notificationId, notification)

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