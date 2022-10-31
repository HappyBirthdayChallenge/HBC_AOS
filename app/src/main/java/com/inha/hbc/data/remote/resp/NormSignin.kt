package com.inha.hbc.data.remote.resp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class NormSignin


@Serializable
@SerialName("R-M011")
data class Success(
    @SerialName("data") var token: Data,
    var message: String,
    var status: Int
): NormSignin()

@SerialName("R-M010")
@Serializable
data class Invalid (
    var data: String?,
    var message: String,
    var status: Int

    ): NormSignin()

@Serializable
@SerialName("E")
data class NoInput (
    var errors: Error?,
    var message: String,
    var status: Int,
    var code: String
): NormSignin()
