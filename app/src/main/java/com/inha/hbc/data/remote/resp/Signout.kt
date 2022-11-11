package com.inha.hbc.data.remote.resp

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator


@Serializable
@JsonClassDiscriminator("type")
sealed class Signout ()

@Serializable
data class SignoutSuccess(
    val code: String,
    val data: String?,
    val message: String,
    val status: Int
    ): Signout()

@Serializable
data class SignoutFailure(
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int
): Signout()
