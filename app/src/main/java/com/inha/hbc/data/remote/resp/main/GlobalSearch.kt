package com.inha.hbc.data.remote.resp.main

import com.inha.hbc.data.remote.resp.Error
import com.inha.hbc.data.remote.resp.message.Member
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class GlobalSearch

@Serializable
@SerialName("R")
data class GlobalSearchSuccess(
    val data: SearchData?,
    val message: String,
    val code: String,
    val status: Int
):GlobalSearch()

@Serializable
@SerialName("E")
data class GlobalSearchFailure(
    var code: String?,
    var errors: Error?,
    var message: String?,
    var status: Int?
):GlobalSearch()

@Serializable
data class SearchData(
    val result: List<Result>
)

@Serializable
data class Result(
    val follow: Boolean,
    val member: Member
)