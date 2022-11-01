package com.inha.hbc.data.remote.resp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class FindPw

@Serializable
@SerialName("R")
data class FindPwSuccess(
    var data: String?,
    var message: String?,
    var status: Int?,
    var code: String
): FindPw()

@Serializable
@SerialName("E")
data class FindPwFailure(
    val code: String,
    val errors: List<Error>,
    val message: String,
    val status: Int
): FindPw()
