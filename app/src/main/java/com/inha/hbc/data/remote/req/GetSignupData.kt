package com.inha.hbc.data.remote.req

data class GetSignupData(
    val birth_date: BirthDate,
    val key: String,
    val name: String,
    val password: String,
    val password_check: String,
    val phone: String,
    val username: String
)

data class BirthDate(
    val date: Int,
    val month: Int,
    val type: String,
    val year: Int
)