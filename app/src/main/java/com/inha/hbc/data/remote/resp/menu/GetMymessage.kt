package com.inha.hbc.data.remote.resp.menu

import com.inha.hbc.data.remote.resp.Error
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class GetMymessage

@Serializable
@SerialName("R")
data class GetMymessageSuccess(
    val code: String,
    val data: MessageData,
    val message: String,
    val status: Int

): GetMymessage()

@Serializable
@SerialName("E")
data class GetMymessageFailure(
    var code: String?,
    var errors: Error?,
    var message: String?,
    var status: Int?
): GetMymessage()

@Serializable
data class MessageData(
    val page: Page
)

@Serializable
data class Page(
    val content: List<Content>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX,
    val totalElements: Int,
    val totalPages: Int
)

@Serializable
data class Content(
    val decoration_type: String,
    val like: Boolean,
    val message_id: Int,
    val read: Boolean,
    val room_id: Int,
    val room_owner: RoomOwner
)


@Serializable
data class RoomOwner(
    val birth_date: BirthDate,
    val id: Int,
    val image_url: String,
    val name: String,
    val username: String
)