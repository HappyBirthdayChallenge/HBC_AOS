package com.inha.hbc.data.remote.resp

data class ErrorResp(
    var status: Int,
    var code: String,
    var message: String,
    var errors: List<Error>?
)
