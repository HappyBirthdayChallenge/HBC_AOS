package com.inha.hbc.util

import android.app.Application
import com.inha.hbc.BuildConfig
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()


        KakaoSdk.init(this, "${BuildConfig.KAKAO_API_KEY}")
    }
}