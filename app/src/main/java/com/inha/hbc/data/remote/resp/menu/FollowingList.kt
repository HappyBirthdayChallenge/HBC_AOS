package com.inha.hbc.data.remote.resp.menu

import android.os.Parcelable
import com.inha.hbc.data.remote.resp.Error
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class FollowingList

@Serializable
@SerialName("R")
data class FollowingListSuccess(
    val data: FollowingPageFront,
    val message: String,
    val code: String,
    val status: Int
): FollowingList()

@Serializable
@SerialName("E")
data class FollowingListFailure(
    var code: String?,
    var errors: Error?,
    var message: String?,
    var status: Int?
): FollowingList()

@Serializable
data class FollowingPageFront(
    val page: FollowingPage
)

@Serializable
data class FollowingPage(
    val content: List<FollowingContent>,
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
data class FollowingContent(
    val following: Following
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
data class Following(
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