package com.inha.hbc.data.remote.resp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class CheckToken

@Serializable
@SerialName("R")
data class CheckTokenSuccess(
    val code: String,
    val data: String?,
    val message: String,
    val status: Int
): CheckToken()


@Serializable
@SerialName("E")
data class CheckTokenFailure(
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int
): CheckToken()
