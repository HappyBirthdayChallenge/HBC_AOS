package com.inha.hbc.data.remote.resp

import kotlinx.serialization.json.JsonClassDiscriminator
import kotlinx.serialization.Serializable
@Serializable
@JsonClassDiscriminator("code")
sealed class NormSignin
