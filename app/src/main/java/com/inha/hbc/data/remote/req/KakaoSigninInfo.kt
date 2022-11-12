package com.inha.hbc.data.remote.req

data class KakaoSigninInfo (
    val fcm_token: String,
    val oauth2_token: String,
    val provider: String
)
