package com.inha.hbc.data.remote.resp

import com.google.gson.annotations.SerializedName

data class NormSigninBody(
    var code: String?,
    @SerializedName(value = "data") var token: Data?,
    var message: String?,
    var status: Int?
)

data class Data(
    @SerializedName(value = "access_token") var accessToken: String,
    @SerializedName(value = "refresh_token") var refreshToken: String
)