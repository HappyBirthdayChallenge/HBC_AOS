package com.inha.hbc.data.remote.resp.message

import com.inha.hbc.data.remote.resp.Error
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class SearchDeco

@Serializable
@SerialName("R")
data class SearchDecoSuccess(
    val data: SearchData?,
    val message: String,
    val code: String,
    val status: Int
): SearchDeco()

@Serializable
@SerialName("E")
data class SearchDecoFailure(
    val code: String,
    val errors: Error,
    val message: String,
    val status: Int?
): SearchDeco()

@Serializable
data class SearchData (
    val dolls: List<Doll>,
    val drinks: List<Drink>,
    val empty: Boolean,
    val first: Boolean,
    val foods: List<Food>,
    val gifts: List<Gift>,
    val last: Boolean,
    val photos: List<Photo>,
    val total_elements: Int,
    val total_pages: Int
)

@Serializable
data class Doll(
    val decoration_type: String,
    val message_id: Int
)

@Serializable
data class Drink(
    val decoration_type: String,
    val message_id: Int
)

@Serializable
data class Food(
    val decoration_type: String,
    val message_id: Int
)

@Serializable
data class Gift(
    val decoration_type: String,
    val message_id: Int
)

@Serializable
data class Photo(
    val decoration_type: String,
    val message_id: Int
)