package com.inha.hbc.data.remote.resp.message

import com.inha.hbc.data.remote.resp.Error
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class MessageLike

@Serializable
@SerialName("R")
data class MessageLikeSuccess(
val data: String?,
val message: String,
val code: String,
val status: Int
    ): MessageLike()

@Serializable
@SerialName("E")
data class MessageLikeFailure(
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int?
    ): MessageLike()