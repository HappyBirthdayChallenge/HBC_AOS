package com.inha.hbc.data.remote.resp.menu

import android.os.Parcelable
import com.inha.hbc.data.remote.resp.Error
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class Friendlist

@Serializable
@SerialName("R")
data class FriendlistSuccess(
    val data: PageFront,
    val message: String,
    val code: String,
    val status: Int
): Friendlist()

@Serializable
@SerialName("E")
data class FriendlistFailure(
    var code: String?,
    var errors: Error?,
    var message: String?,
    var status: Int?
): Friendlist()

@Serializable
data class PageFront(
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
    val member: Member
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
data class Member(
    val birth_date: BirthDate,
    val id: Int,
    val image_url: String,
    val name: String,
    val username: String
)

@Serializable
@Parcelize
data class BirthDate(
    val date: Int,
    val month: Int,
    val type: String,
    val year: Int
): Parcelable