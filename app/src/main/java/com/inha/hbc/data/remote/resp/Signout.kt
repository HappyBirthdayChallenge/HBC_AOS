package com.inha.hbc.data.remote.resp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator


@Serializable
@JsonClassDiscriminator("type")
sealed class Signout ()

@Serializable
@SerialName("R")
data class SignoutSuccess(
    val code: String,
    val data: String?,
    val message: String,
    val status: Int
    ): Signout()

@Serializable
@SerialName("E")
data class SignoutFailure(
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int
): Signout()
