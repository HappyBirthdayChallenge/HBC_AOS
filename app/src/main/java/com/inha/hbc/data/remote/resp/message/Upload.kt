package com.inha.hbc.data.remote.resp.message

import com.inha.hbc.data.remote.resp.Error
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class Upload

@Serializable
@SerialName("R")
data class UploadSuccess(
    val data: List<RoomInfoData>?,
    val message: String,
    val code: String,
    val status: Int
): Upload()

@Serializable
@SerialName("E")
data class UploadFailure(
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int?
): Upload()

@Serializable
data class FileId(
    val file_id: String
)
