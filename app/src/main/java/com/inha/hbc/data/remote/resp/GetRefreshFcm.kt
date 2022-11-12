package com.inha.hbc.data.remote.resp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class GetRefreshFcm

@Serializable
@SerialName("R")
data class GetRefreshFcmSuccess(
    val status: Int,
    val data: String?,
    val code: String,
    val message: String
): GetRefreshFcm()

@Serializable
@SerialName("E")
data class GetRefreshFcmFailure(
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int?
): GetRefreshFcm()