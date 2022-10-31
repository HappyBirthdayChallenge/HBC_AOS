package com.inha.hbc.data.remote.resp
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class CheckBirth

@Serializable
data class BirthSuccess (
    val status: Int,
    val data: Data,
    val code: String,
    val message: String
        ): CheckBirth()

@Serializable
data class BirthFailure (
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int?
        ): CheckBirth()