package com.inha.hbc.data.remote.resp

data class NormSigninBody(
    val code: String,
    val errors: List<Error>,
    val message: String,
    val status: Int
)
data class Error(
    val field: String,
    val reason: String,
    val value: String
)