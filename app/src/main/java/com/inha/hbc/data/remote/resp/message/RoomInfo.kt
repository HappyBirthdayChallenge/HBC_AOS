package com.inha.hbc.data.remote.resp.message

import com.inha.hbc.data.remote.resp.Error
import com.inha.hbc.data.remote.resp.menu.BirthDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class RoomInfo

@Serializable
@SerialName("R")
data class RoomInfoSuccess(
    val data: List<RoomInfoData>?,
    val message: String,
    val code: String,
    val status: Int
): RoomInfo()

@Serializable
@SerialName("E")
data class RoomInfoFailure(
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int?
): RoomInfo()

@Serializable
data class RoomInfoData(
    val cake_type: String,
    val room_id: Int,
    val room_type: String,
    val birth_date: BirthDate
)