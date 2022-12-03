package com.inha.hbc.util.network.menu

import com.inha.hbc.data.remote.resp.menu.FollowerList
import com.inha.hbc.data.remote.resp.menu.FollowingList
import com.inha.hbc.data.remote.resp.menu.GetMymessage
import com.inha.hbc.data.remote.resp.menu.GetProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MenuRetroServiceInterface {
    @GET("/members/friends/followings")
    fun getFollowingList(
        @Query("page") page: String,
        @Query("size") size: String
    ): Call<List<FollowingList>>

    @GET("/members/friends/followers")
    fun getFollowerList(
        @Query("page") page: String,
        @Query("size") size: String
    ): Call<List<FollowerList>>

    @GET("/members/accounts/profile/{member_id}")
    fun getProfile(
        @Path("member_id") member_id: String
    ): Call<List<GetProfile>>

    @GET("/messages")
    fun getMymessage(
        @Query("page") page: String,
        @Query("size") size: String
    ): Call<List<GetMymessage>>
}