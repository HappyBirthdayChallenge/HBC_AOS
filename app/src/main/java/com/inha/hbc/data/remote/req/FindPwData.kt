package com.inha.hbc.data.remote.req

data class FindPwData(
    val key: String,
    val name: String,
    val password: String,
    val passwordCheck: String,
    val phone: String,
    val username: String
)