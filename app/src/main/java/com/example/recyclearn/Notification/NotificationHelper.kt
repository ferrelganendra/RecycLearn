package com.example.recyclearn.Notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.recyclearn.Activity.MainActivity
import kotlin.random.Random

class NotificationHelper(private val context: Context) {

    private val channelId = "random_notification_channel"
    private val channelName = "Random Notifications"

    fun showRandomNotification() {
        val messages = listOf(
            "Haii! Semangat terus yaa🤍",
            "Kurangi penggunaan sampah plastik yaa",
            "Jangan lupa buang sampah pada tempatnya yaa!",
            "Ayo, berkontribusi dalam mengurangi sampah di sekitar kita.",
            "Cintai bumi seperti kita mencitai dia🌏"
        )

        val randomMessage = messages[Random.nextInt(messages.size)]
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel for Android 8.0 and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Save the Earth")
            .setContentText(randomMessage)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}

