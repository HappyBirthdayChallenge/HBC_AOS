package com.inha.hbc.data.remote.resp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class KakaoSignin

@Serializable
@SerialName("R")
data class KakaoSuccess(
    val status: Int,
    val data: Data,
    val code: String,
    val message: String
): KakaoSignin()

@Serializable
@SerialName("E")
data class KakaoFailure(
    var code: String?,
    var errors: Error?,
    var message: String?,
    var status: Int?
): KakaoSignin()