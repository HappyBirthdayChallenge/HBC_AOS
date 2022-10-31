package com.inha.hbc.data.remote.resp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class GetSignup

@Serializable
@SerialName("R")
data class SignupSuccess(
    val status: Int,
    val data: Data,
    val code: String,
    val message: String
): GetSignup()

@Serializable
@SerialName("E")
data class SignupFailure (
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int?
        ): GetSignup()