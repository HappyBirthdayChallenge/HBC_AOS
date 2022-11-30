package com.inha.hbc.data.remote.resp.message

import com.inha.hbc.data.remote.resp.Error
import com.inha.hbc.data.remote.resp.menu.BirthDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class GetMessage

@Serializable
@SerialName("R")
data class GetMessageSuccess(
    val data: GetMessageData?,
    val message: String,
    val code: String,
    val status: Int
): GetMessage()

@Serializable
@SerialName("E")
data class GetMessageFailure(
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int?
): GetMessage()

@Serializable
data class GetMessageData(
    val animation_type: String,
    val content: String,
    val create_at: String,
    val decoration_type: String,
    val file_uris: List<String>,
    val member: Member,
    val message_id: Int
)

@Serializable
data class Member(
    val birth_date: BirthDate,
    val id: Int,
    val image_url: String,
    val name: String,
    val username: String
)
