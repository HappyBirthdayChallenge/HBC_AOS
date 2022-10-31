package com.inha.hbc.data.remote.resp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Error(
    var field: String,
    var reason: String,
    var value: String
)


@Serializable
data class Data(
    @SerialName(value = "access_token") var accessToken: String,
    @SerialName(value = "refresh_token") var refreshToken: String
)