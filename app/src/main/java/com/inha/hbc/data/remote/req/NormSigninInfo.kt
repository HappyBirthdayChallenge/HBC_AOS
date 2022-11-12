package com.inha.hbc.data.remote.req

@kotlinx.serialization.Serializable
data class NormSigninInfo(
    var fcm_token: String?,
    var password : String?,
    var username: String?
)
