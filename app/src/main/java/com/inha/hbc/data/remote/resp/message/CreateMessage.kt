package com.inha.hbc.data.remote.resp.message

import com.inha.hbc.data.remote.resp.Error
import com.inha.hbc.data.remote.resp.Key
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class CreateMessage

@Serializable
@SerialName("R")
data class CreateMessageSuccess(
    val data: Key,
    val message: String,
    val code: String,
    val status: Int
):CreateMessage()

@Serializable
@SerialName("E")
data class CreateMessageFailure (
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int?
        ): CreateMessage()