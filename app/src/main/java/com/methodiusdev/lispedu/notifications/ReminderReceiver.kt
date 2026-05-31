package com.methodiusdev.lispedu.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.methodiusdev.lispedu.MainActivity
import com.methodiusdev.lispedu.R

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(notificationManager)

        val activityIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            android.app.Notification.Builder(context, ReminderScheduler.CHANNEL_ID)
        } else {
            android.app.Notification.Builder(context)
        }
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Lisp Edu")
            .setContentText("Still learning Common Lisp? Come back for a short lesson.")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(ReminderScheduler.NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            ReminderScheduler.CHANNEL_ID,
            "Lesson reminders",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Reminders after app inactivity"
        }

        notificationManager.createNotificationChannel(channel)
    }
}
