package com.inha.hbc.data.remote.resp.room

import com.inha.hbc.data.remote.resp.Error
import com.inha.hbc.ui.main.view.NotifyData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class FindMymessage

@Serializable
@SerialName("R")
data class FindMymessageSuccess(
    val code: String,
    val data: FindMymessageData,
    val message: String,
    val status: Int
):FindMymessage()

@Serializable
@SerialName("E")
data class FindMymessageFailure(
    var code: String?,
    var errors: Error?,
    var message: String?,
    var status: Int?
):FindMymessage()

@Serializable
data class FindMymessageData(
    val found: Boolean,
    val index: Int?,
    val page: Int?
)