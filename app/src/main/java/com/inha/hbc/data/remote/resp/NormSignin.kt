package com.inha.hbc.data.remote.resp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class NormSignin


@Serializable
@SerialName("R")
data class NormSuccess(
    @SerialName("data") var token: Data,
    var message: String,
    var status: Int,
    var code: String
): NormSignin()


@Serializable
@SerialName("E")
data class NormFailure (
    var errors: List<Error>,
    var message: String,
    var status: Int,
    var code: String
): NormSignin()
