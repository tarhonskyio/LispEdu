package com.methodiusdev.lispedu.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

object ReminderScheduler {
    const val CHANNEL_ID = "lesson_reminders"
    const val NOTIFICATION_ID = 1001

    private const val REQUEST_CODE = 2001
    private const val INACTIVITY_REMINDER_INTERVAL_MILLIS = 5 * 60 * 1000L

    fun scheduleInactivityReminders(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + INACTIVITY_REMINDER_INTERVAL_MILLIS,
            INACTIVITY_REMINDER_INTERVAL_MILLIS,
            reminderPendingIntent(context)
        )
    }

    fun cancelInactivityReminders(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(reminderPendingIntent(context))
    }

    private fun reminderPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, ReminderReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
