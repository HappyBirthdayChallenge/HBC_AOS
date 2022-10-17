package com.inha.hbc.data.remote.resp

import com.google.gson.annotations.SerializedName

data class KakaoSigninBody(
    val code: String,
    val data: KakaoData,
    val message: String,
    val status: Int
)

data class KakaoData(
    @SerializedName(value ="access_token") val accessToken : String,
    @SerializedName(value ="refresh_token") val refreshToken : String
)