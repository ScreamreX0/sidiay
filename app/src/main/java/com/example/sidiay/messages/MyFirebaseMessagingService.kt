package com.example.sidiay.messages

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.core.utils.Logger
import com.example.sidiay.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        getFirebaseMessage(message.notification?.title, message.notification?.body)
    }

    private fun getFirebaseMessage(title: String?, message: String?) {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "messagesFirebaseChannel")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(com.example.core.R.drawable.ic_baseline_notifications_24)
            .setAutoCancel(false);

        val manager: NotificationManagerCompat = NotificationManagerCompat.from(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Logger.m("Permissions for messaging not granted")
            return
        }
        manager.notify(101, builder.build())
    }
}