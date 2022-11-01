package com.inha.hbc.data.remote.resp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class FindId

@Serializable
@SerialName("R")
data class FindIdSuccess(
    var data: IdData?,
    var message: String?,
    var status: Int?,
    var code: String
): FindId()

@Serializable
@SerialName("E")
data class FindIdFailure(
    val code: String,
    val errors: List<Error>,
    val message: String,
    val status: Int
): FindId()

@Serializable
data class IdData (
    val username: String
        )