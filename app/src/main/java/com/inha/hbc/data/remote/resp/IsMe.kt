package com.inha.hbc.data.remote.resp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class IsMe

@Serializable
@SerialName("R")
data class IsMeSuccess(
    val code: String,
    val data: String?,
    val message: String,
    val status: Int

): IsMe()

@Serializable
@SerialName("E")
data class IsMeFailure(
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int
): IsMe()
