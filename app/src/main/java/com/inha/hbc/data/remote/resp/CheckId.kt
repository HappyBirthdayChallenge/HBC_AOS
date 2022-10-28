package com.inha.hbc.data.remote.resp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class CheckId {
    abstract var code: String
}

@Serializable
@SerialName("R")
data class IdValid(
    var data: String?,
    var message: String?,
    var status: Int?,
    override var code: String
):CheckId()



@Serializable
@SerialName("E")
data class IdErr(
    override var code: String,
    val errors: List<Error>,
    val message: String,
    val status: Int
):CheckId()
