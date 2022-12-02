package com.inha.hbc.data.remote.resp.menu

import com.inha.hbc.data.remote.resp.Error
import com.inha.hbc.data.remote.resp.message.Member
import com.inha.hbc.data.remote.resp.message.RoomInfoData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class GetProfile

@Serializable
@SerialName("R")
data class GetProfileSuccess(
    val data: Proflie,
    val message: String,
    val code: String,
    val status: Int
): GetProfile()

@Serializable
@SerialName("E")
data class GetProfileFailure(
    var code: String?,
    var errors: Error?,
    var message: String?,
    var status: Int?
): GetProfile()


@Serializable
data class Proflie (
    val follow: Boolean,
    val followers: Int,
    val followings: Int,
    val member: Member,
    val message_likes: Int,
    val rooms: List<RoomInfoData>
        )