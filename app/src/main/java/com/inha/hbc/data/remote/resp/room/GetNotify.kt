package com.inha.hbc.ui.main.view

import com.inha.hbc.data.remote.resp.Error
import com.inha.hbc.data.remote.resp.room.ReceiveMessageData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class GetNotify

@Serializable
@SerialName("R")
data class GetNotifySuccess(
    val code: String,
    val data: NotifyData,
    val message: String,
    val status: Int
): GetNotify()

@Serializable
@SerialName("E")
data class GetNotifyFailure(
    var code: String?,
    var errors: Error?,
    var message: String?,
    var status: Int?
): GetNotify()

@Serializable
data class NotifyData(
    val page: Page
)

@Serializable
data class Page(
    val content: List<NotifyContent>,
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
data class NotifyContent(
    val alarm_id: Int,
    val alarm_type: String,
    val content: String,
    val create_at: String,
    var friend_alarm: FriendAlarm? = null,
    var message_alarm: MessageAlarm? = null,
    var message_like_alarm: MessageLikeAlarm? = null,
    var room_alarm: RoomAlarm? = null
)

@Serializable
data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: Sort,
    val unpaged: Boolean
)

@Serializable
data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

@Serializable
data class SortX(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

@Serializable
data class FriendAlarm(
    val follow: Boolean,
    val member: Member
)

@Serializable
data class MessageAlarm(
    val decoration_type: String,
    val message_id: Int,
    val room_id: Int
)

@Serializable
data class MessageLikeAlarm(
    val decoration_type: String,
    val member: Member,
    val message_id: Int,
    val room_id: Int
)

@Serializable
data class RoomAlarm(
    val member: Member,
    val room_id: Int
)

@Serializable
data class Member(
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

