package com.inha.hbc.data.remote.resp.menu

import com.inha.hbc.data.remote.resp.Error
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class AddFriend

@Serializable
@SerialName("R")
data class AddFriendSuccess(
    val data: String?,
    val message: String,
    val code: String,
    val status: Int
): AddFriend()

@Serializable
@SerialName("E")
data class AddFriendFailure(
    var code: String?,
    var errors: Error?,
    var message: String?,
    var status: Int?
): AddFriend()
