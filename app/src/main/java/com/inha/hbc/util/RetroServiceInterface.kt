package com.inha.hbc.util

import com.inha.hbc.data.remote.req.NormSigninInfo
import com.inha.hbc.data.remote.resp.KakaoSigninBody
import com.inha.hbc.data.remote.resp.NormSigninBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroServiceInterface {
    @POST("/auth/signin")
    fun normalSignin(
        @Body userInfo: NormSigninInfo
    ) : Call<NormSigninBody>


    @POST("/oauth2/signin/{provider}")
    fun kakaoSignin(
        @Path("provider") provider: String,
        @Query("token") token: String
    ) : Call<KakaoSigninBody>
}