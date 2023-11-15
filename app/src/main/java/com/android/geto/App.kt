package com.android.geto

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val channelId = getString(R.string.geto_notification_channel_id)

        val channelTitle = getString(R.string.geto_notification_channel_title)

        val channelDescription = getString(R.string.geto_notification_channel_description)

        val importance = NotificationManager.IMPORTANCE_HIGH

        val mChannel = NotificationChannel(channelId, channelTitle, importance)

        mChannel.description = channelDescription

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(mChannel)
    }
}