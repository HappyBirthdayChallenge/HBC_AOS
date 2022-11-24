package com.inha.hbc.data.remote.resp.message

import com.inha.hbc.data.remote.resp.Error
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class UploadMessage

@Serializable
@SerialName("R")
data class UploadMessageSuccess(
    val code: String,
    val data: String?,
    val message: String,
    val status: Int
): UploadMessage()

@Serializable
@SerialName("E")
data class UploadMessageFailure(
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int?
): UploadMessage()
