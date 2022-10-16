package com.inha.hbc.data.local

data class Jwt(
    val authorities: List<String>,
    val birth: Birth,
    val email: String,
    val exp: Int,
    val iat: Int,
    val id: Int,
    val img: String,
    val isr: String,
    val name: String,
    val sub: String,
    val typ: String,
    val uname: String
)

data class Birth(
    val date: Int,
    val month: Int,
    val type: String,
    val year: Int
)