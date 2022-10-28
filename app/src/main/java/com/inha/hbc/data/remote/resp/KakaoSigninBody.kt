package com.inha.hbc.data.remote.resp


data class kakaoSigninBody(
    var code: String?,
    var errors: List<Error>?,
    var message: String?,
    var status: Int?
)