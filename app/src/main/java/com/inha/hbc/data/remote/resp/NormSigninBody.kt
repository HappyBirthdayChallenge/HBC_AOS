package com.inha.hbc.data.remote.resp

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
@SerialName("")
data class NormSigninBody(
    val code: String,
    val errors: List<Error>?,
    @SerializedName(value = "data") val token: Data?,
    val message: String,
    val status: Int
)

data class Error(
    val field: String,
    val reason: String,
    val value: String
)

data class Data(
    @SerializedName(value = "access_token") val accessToken: String,
    @SerializedName(value = "refresh_token") val refreshToken: String
)