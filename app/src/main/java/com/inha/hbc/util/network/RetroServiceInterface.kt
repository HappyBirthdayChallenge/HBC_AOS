package com.inha.hbc.util.network

import com.inha.hbc.data.remote.req.*
import com.inha.hbc.data.remote.resp.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroServiceInterface {
    @POST("/auth/signin")
    fun normalSignin(
        @Body userInfo: NormSigninInfo
    ) : Call<List<NormSignin>>


    @POST("/oauth2/signin")
    fun kakaoSignin(
        @Body data: KakaoSigninInfo
    ) : Call<List<KakaoSignin>>


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
    ): Call<List<CheckCode>>

    @POST("/members/accounts/birthday")
    fun checkBirth(
        @Body birthDate: CheckBirthData
    ): Call<List<CheckBirth>>

    @POST("/auth/signup")
    fun getSignup(
        @Body allData: GetSignupData
    ): Call<List<GetSignup>>

    @POST("/auth/identify")
    fun isMe(
        @Body meData: IsMeData
    ): Call<List<IsMe>>

    @POST("/auth/find/username")
    fun findId(
        @Body idData: FindIdData
    ): Call<List<FindId>>

    @POST("/auth/find/password")
    fun findPw(
        @Body pwData: FindPwData
    ): Call<List<FindPw>>

    @POST("/token/jwt/reissue")
    fun getToken(
        @Query("refreshToken") refreshToken: String
    ):Call<List<GetToken>>

    @POST("/token/jwt/check")
    fun checkToken(
    ):Call<List<CheckToken>>

    @POST("/members/accounts/signout")
    fun signout(
        @Query("fcm_token") fcmToken: String
    ): Call<List<Signout>>

    @GET("/members/accounts/me")
    fun getMyInfo(
    ): Call<List<GetMyInfo>>

    @POST("/token/fcm/refresh")
    fun refreshFcm(
        @Query("fcm_token") fcmToken: String
    ): Call<List<GetRefreshFcm>>
}