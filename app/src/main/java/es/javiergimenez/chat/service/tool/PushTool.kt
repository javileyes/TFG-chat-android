package es.javiergimenez.chat.service.tool

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import es.javiergimenez.chat.R
import es.javiergimenez.chat.controller.SplashActivity


object PushTool {

    private const val GROUP_KEY_PUSH_NOTIFICATION = "GROUP"
    private const val CHANNEL_ID = "CHAT"


    fun show(context: Context, notification: RemoteMessage.Notification?) {
        val random = (Math.random() * 5).toInt()
        val title = if (notification != null) notification.title else "Title $random"
        val body = if (notification != null) notification.body!! else "Body $random"

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notifications_ic) // notification icon
            .setContentTitle(title) // title for notification
            .setContentText(body) // message for notification
            //                .setGroupSummary(true)
            .setGroup(GROUP_KEY_PUSH_NOTIFICATION)
            //                .setGroup("" + System.currentTimeMillis())
            .setAutoCancel(true) // clear notification after click

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        builder.setSound(alarmSound)

        val intent = Intent(context, SplashActivity::class.java)
        intent.action = "android.intent.action.MAIN"
        intent.addCategory("android.intent.category.LAUNCHER")
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        builder.setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = (Math.random() * 15000).toInt()

        notificationManager.notify(1, createSummary(context, body))
        notificationManager.notify(id, builder.build())
    }

    fun removeAll(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }

    private fun createSummary(context: Context, body: String): Notification {
        val intent = Intent(context, SplashActivity::class.java)
        intent.action = "android.intent.action.MAIN"
        intent.addCategory("android.intent.category.LAUNCHER")
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notifications_ic)
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setContentText(body)
            .setGroupSummary(true)
            .setGroup(GROUP_KEY_PUSH_NOTIFICATION)
            .setContentIntent(pendingIntent).build()
    }

}