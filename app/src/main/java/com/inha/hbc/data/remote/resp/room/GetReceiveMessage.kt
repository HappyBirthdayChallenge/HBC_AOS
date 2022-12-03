package com.inha.hbc.data.remote.resp.room

import com.inha.hbc.data.remote.resp.Error
import com.inha.hbc.data.remote.resp.menu.MessageData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class GetReceiveMessage

@Serializable
@SerialName("R")
data class GetReceiveMessageSuccess(
    val code: String,
    val data: ReceiveMessageData,
    val message: String,
    val status: Int
)

@Serializable
@SerialName("E")
data class GetReceiveMessageFailure(
    var code: String?,
    var errors: Error?,
    var message: String?,
    var status: Int?
)

@Serializable
data class ReceiveMessageData(
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
    val create_at: String,
    val decoration_type: String,
    val like: Boolean,
    val message_id: Int,
    val read: Boolean,
    val writer: Writer
)

@Serializable
data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: SortX,
    val unpaged: Boolean
)

@Serializable
data class SortX(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

@Serializable
data class Writer(
    val birth_date: BirthDate,
    val id: Int,
    val image_url: String,
    val name: String,
    val username: String
)

@Serializable
data class BirthDate(
    val date: Int,
    val month: Int,
    val type: String,
    val year: Int
)