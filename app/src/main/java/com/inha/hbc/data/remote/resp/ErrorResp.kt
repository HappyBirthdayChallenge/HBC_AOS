package com.inha.hbc.data.remote.resp

data class ErrorResp(
    var status: Int,
    var code: String,
    var message: String,
    var errors: List<Error>?
)
data class Error(
    var field: String?,
    var reason: String?,
    var value: String?
)
