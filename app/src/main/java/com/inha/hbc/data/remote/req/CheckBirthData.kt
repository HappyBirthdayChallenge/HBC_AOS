package com.inha.hbc.data.remote.req

data class CheckBirthData(
    val date: Int,
    val month: Int,
    val type: String,
    val year: Int
)