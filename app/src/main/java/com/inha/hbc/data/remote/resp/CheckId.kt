package com.inha.hbc.data.remote.resp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("code")
sealed class CheckId

@Serializable
@SerialName("R-M001")
data class IdValid(
    var data: String?,
    var message: String?,
    var status: Int?
):CheckId()