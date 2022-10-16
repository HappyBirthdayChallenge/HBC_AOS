package com.inha.hbc.util

import android.app.Application
import com.inha.hbc.BuildConfig
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }


    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        KakaoSdk.init(this, "${BuildConfig.KAKAO_API_KEY}")


        super.onCreate()
    }
}