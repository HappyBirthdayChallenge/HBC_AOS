package com.inha.hbc.data.remote.resp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class CheckCode

@Serializable
@SerialName("R")
data class CodeSuccess(
    val data: Key,
    val message: String,
    val code: String,
    val status: Int
): CheckCode()

@Serializable
@SerialName("E")
data class CodeFailure(
    val code: String,
    val errors: Error?,
    val message: String?,
    val status: Int?
): CheckCode()

@Serializable
data class Key(
    val key: String
)
