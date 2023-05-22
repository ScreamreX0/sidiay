package com.example.sidiay.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.core.utils.Logger
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Logger.m("Revieved message from server: ${message.notification?.body}")

        message.notification?.let {
            getFirebaseMessage(it.title, it.body)
        } ?: run { Logger.m("Message notification is null") }
    }

    private fun getFirebaseMessage(title: String?, message: String?) {
        val channelId = "messagesFirebaseChannel"
        val channelName = "Messages"

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(com.example.core.R.drawable.ic_baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        manager.createNotificationChannel(channel)
        manager.notify(1, builder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Logger.m("Token refreshed: $token")
    }
}