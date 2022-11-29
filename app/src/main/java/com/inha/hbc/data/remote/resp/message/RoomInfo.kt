package com.inha.hbc.data.remote.resp.message

import android.os.Parcel
import android.os.Parcelable
import com.inha.hbc.data.remote.resp.Error
import com.inha.hbc.data.remote.resp.menu.BirthDate
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class RoomInfo

@Serializable
@SerialName("R")
@Parcelize
data class RoomInfoSuccess(
    val data: List<RoomInfoData>?,
    val message: String,
    val code: String,
    val status: Int
): RoomInfo(), Parcelable

@Serializable
@SerialName("E")
data class RoomInfoFailure(
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int?
): RoomInfo()

@Serializable
@Parcelize
data class RoomInfoData(
    val cake_type: String,
    val room_id: Int,
    val room_type: String,
    val birth_date: BirthDate
): Parcelable