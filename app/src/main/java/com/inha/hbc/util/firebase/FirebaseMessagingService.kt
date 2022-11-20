package com.inha.hbc.util.firebase

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.inha.hbc.util.network.RetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication

class FirebaseMessagingService: FirebaseMessagingService() {

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
    

}