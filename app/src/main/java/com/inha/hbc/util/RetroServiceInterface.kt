package com.inha.hbc.util

import com.inha.hbc.data.remote.req.NormSigninInfo
import com.inha.hbc.data.remote.resp.NormSigninBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetroServiceInterface {
    @POST("/auth/signin")
    fun normalSignin(
        @Body userInfo: NormSigninInfo
    ) : Call<NormSigninBody>
}