package com.inha.hbc.data.remote.resp.menu

import com.inha.hbc.data.remote.resp.Error
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class FollowerList

@Serializable
@SerialName("R")
data class FollowerListSuccess(
    val data: FollowerPageFront,
    val message: String,
    val code: String,
    val status: Int
): FollowerList()

@Serializable
@SerialName("E")
data class FollowerListFailure(
    var code: String?,
    var errors: Error?,
    var message: String?,
    var status: Int?
): FollowerList()

@Serializable
data class FollowerPageFront(
    val page: FollowerPage
)

@Serializable
data class FollowerPage(
    val content: List<FollowerContent>,
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
data class FollowerContent(
    val follow: Boolean,
    val member: Follower
)

@Serializable
data class Follower(
    val birth_date: BirthDate,
    val id: Int,
    val image_url: String,
    val name: String,
    val username: String
)
