package com.jw.trainmepractice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFcmService: FirebaseMessagingService() {

//    override fun onNewToken(p0: String) {
//        super.onNewToken(p0)
//
//        // FCM메세징을 위한 토큰이 갱신되면 sp에다가 저장
//        val sp1 = getSharedPreferences("user", Context.MODE_PRIVATE)
//        val sp2 = getSharedPreferences("trainer", Context.MODE_PRIVATE)
//        val editor = sp1.edit()
//        editor.putString("FCMToken",p0).apply()
//        sp2.edit().putString("FCMToken",p0).apply()
//    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        if(p0.data.size > 0){
            val text = p0.data.get("text")
            val from = p0.data.get("from")
            showNotification(text, from)
        }
        if(p0.notification != null){
            showNotification(p0.notification!!.title, p0.notification!!.body)
        }
}
    private fun showNotification(text: String?, from: String?){
        val intent = Intent(this, ChatRoom::class.java)
        val channel_id = "default_channel_id"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val array = arrayOf(1000,1000,1000,1000,1000) as LongArray
        val builder = NotificationCompat.Builder(applicationContext, channel_id)
            .setSound(uri)
            .setAutoCancel(true)
            .setVibrate(array)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContentTitle(from)
            .setContentText(text)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel("default_channel_id","default_channel_id",NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.setSound(uri,null)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0,builder.build())

    }
}