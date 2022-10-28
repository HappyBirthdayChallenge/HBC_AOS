package com.inha.hbc.util

import com.inha.hbc.data.remote.req.BirthDateInfo
import com.inha.hbc.data.remote.req.CheckCodeData
import com.inha.hbc.data.remote.req.CheckPhoneData
import com.inha.hbc.data.remote.req.NormSigninInfo
import com.inha.hbc.data.remote.resp.CheckId
import com.inha.hbc.data.remote.resp.CheckPhone
import com.inha.hbc.data.remote.resp.NormSignin
import com.inha.hbc.data.remote.resp.kakaoSigninBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroServiceInterface {
    @POST("/auth/signin")
    fun normalSignin(
        @Body userInfo: NormSigninInfo
    ) : Call<List<NormSignin>>


    @POST("/oauth2/signin/{provider}")
    fun kakaoSignin(
        @Path("provider") provider: String,
        @Query("token") token: String
    ) : Call<kakaoSigninBody>


    @POST("/members/accounts/birthday")
    fun setBirth(
        @Body birthDate: BirthDateInfo
    ): Call<NormSignin>


    @POST("/auth/check/username")
    fun checkId(
        @Body id: String
    ): Call<List<CheckId>>

    @POST("/auth/check/phone")
    fun checkPhone(
        @Body phoneData: CheckPhoneData
    ): Call<List<CheckPhone>>

    @POST("/auth/send/code")
    fun reqCode(
        @Body phoneData: CheckPhoneData
    ): Call<List<CheckPhone>>

    @POST("/auth/verify/code")
    fun checkCode(
        @Body checkCodeData: CheckCodeData
    ): Call<List<CheckPhone>>
}