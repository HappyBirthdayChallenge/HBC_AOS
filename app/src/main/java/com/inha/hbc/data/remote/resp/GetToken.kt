package com.inha.hbc.data.remote.resp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class GetToken

@Serializable
@SerialName("R")
data class GetTokenSuccess(
    val code: String,
    val data: Data?,
    val message: String,
    val status: Int
):GetToken()

@Serializable
@SerialName("E")
data class GetTokenFailure(
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int
): GetToken()
