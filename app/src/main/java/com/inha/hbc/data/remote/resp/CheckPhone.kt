package com.inha.hbc.data.remote.resp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class CheckPhone

@Serializable
@SerialName("R")
data class PhoneSuccess(
    val code: String,
    val data: String?,
    val message: String?,
    val status: Int?
        ): CheckPhone()

@Serializable
@SerialName("E")
data class PhoneFailure(
    val code: String,
    val errors: Error?,
    val message: String?,
    val status: Int?
): CheckPhone()