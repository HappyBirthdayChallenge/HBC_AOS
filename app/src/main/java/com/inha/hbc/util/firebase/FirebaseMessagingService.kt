package com.inha.hbc.util.firebase

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseMessagingService: FirebaseMessagingService() {

    fun and13() {

    }
    //현재 토큰 검색
   fun searchToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("fcm", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result


        })
    }


    //새 토큰 생성될 때 실행행
    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }
}