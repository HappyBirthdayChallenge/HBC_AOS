package com.inha.hbc.util.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import com.inha.hbc.R
import com.inha.hbc.util.network.RetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication

class FirebaseMessagingService: FirebaseMessagingService() {
    val CHANNEL_ID = "default_noti"
    val CHANNEL_NAME= "HBC_notify"
    val CHANNEL_DESCRIPTION = "알림"
    //현재 토큰 검색(수동 실행 -> 항상 최신 토큰 받아옴)
   fun searchToken() {
        Log.d("getToken","getTok")
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("LoginActivity", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result
            GlobalApplication.prefs.setFcmtoken(token)
        })
    }
    //새 토큰 생성될 때 실행 (토큰 갱신이 되었을때 getToken후 실행됨)
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("getToken","newTok")
        GlobalApplication.prefs.setFcmtoken(token)
        RetrofitService().refreshFcm(GlobalApplication.prefs.getFcmtoken()!!)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val title = message.notification?.title
        val body = message.notification?.body

        makeChannel()

        buildNoti(title!!, body!!)

    }

    fun makeChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESCRIPTION

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)

        }
    }

    fun buildNoti(title: String, message: String) {
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)


        NotificationManagerCompat.from(this)
            .notify(1, notificationBuilder.build())
    }
    

}