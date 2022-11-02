package com.inha.hbc.data.remote.resp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class CheckBirth

@Serializable
@SerialName("R")
data class BirthSuccess (
    val status: Int,
    val data: Data,
    val code: String,
    val message: String
        ): CheckBirth()

@Serializable
@SerialName("E")
data class BirthFailure (
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int?
        ): CheckBirth()