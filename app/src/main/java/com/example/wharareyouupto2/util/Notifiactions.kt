package com.example.wharareyouupto2.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.wharareyouupto2.ui.view.activity.MainActivity

const val NOTIFICATION_ID = "notifyId"
const val CHANNEL_ID = "Channel1"
const val TITLE_EXTRA = "titleExtra"
const val MESSAGE_EXTRA = "message"
const val IMAGE_EXTRA = "image"

class Notifications : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val activityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification  = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(intent.getIntExtra(IMAGE_EXTRA,-1))
            .setContentTitle(intent.getStringExtra(TITLE_EXTRA))
            .setContentText(intent.getStringExtra(MESSAGE_EXTRA))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // Registering our channel with the system
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(intent.getIntExtra(NOTIFICATION_ID,-1), notification)
    }
}