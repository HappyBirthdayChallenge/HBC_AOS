package com.inha.hbc.data.remote.resp

import com.google.gson.annotations.SerializedName

data class KakaoSigninBody(
    val code: String,
    val data: Data,
    val message: String,
    val status: Int
)

data class Data(
    @SerializedName(value ="access_token") val accessToken : String,
    @SerializedName(value ="refresh_token") val refreshToken : String
)