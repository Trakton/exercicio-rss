package br.ufpe.cin.if710.rss

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat

class FeedReceiver: BroadcastReceiver() {

    override fun onReceive(c: Context?, i: Intent?) {
        val manager = c?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val cpu = manager.runningAppProcesses
        if(cpu.any { it.importance == IMPORTANCE_FOREGROUND && it.processName.equals(c.packageName) }){
            Notify(c, i)
        }
    }

    private fun Notify(c: Context?, i: Intent?) {
        val intent = Intent(c, MainActivity::class.java)
        val pending = PendingIntent.getActivity(c, 0, intent, 0)
        val notification = NotificationCompat.Builder(c!!.applicationContext, "br.cin.ufpe.if710.notifications")
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("Check your feed")
                .setContentText("There are news!")
                .setContentIntent(pending)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

        val notificationService = c.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationService.notify(404, notification)
    }
}