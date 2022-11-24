package com.inha.hbc.data.remote.req.message

data class MessageData(
    val animation_type: String,
    val content: String,
    val decoration_type: String,
    val file_ids: List<Int>,
    val message_id: Int
)