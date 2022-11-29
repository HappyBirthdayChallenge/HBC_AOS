package com.inha.hbc.data.remote.resp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class GetMyInfo

@Serializable
@SerialName("R")
@Parcelize
data class GetMyInfoSuccess(
    val status: Int,
    val data: GetMyInfoData?,
    val code: String,
    val message: String
): GetMyInfo(), Parcelable

@Serializable
@SerialName("E")
data class GetMyInfoFailure(
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int?
): GetMyInfo()

@Serializable
@Parcelize
data class GetMyInfoData(
    val authorities: List<String>,
    val birth_date: GetMyInfoBirth,
    val id: Int,
    val image_url: String,
    val name: String,
    val phone: String?,
    val username: String
): Parcelable

@Serializable
@Parcelize
data class GetMyInfoBirth (
    val date: Int,
    val month: Int,
    val type: String,
    val year: Int
):Parcelable