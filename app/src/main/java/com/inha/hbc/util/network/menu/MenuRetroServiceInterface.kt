package com.inha.hbc.util.network.menu

import com.inha.hbc.data.remote.resp.main.GlobalSearch
import com.inha.hbc.data.remote.resp.menu.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MenuRetroServiceInterface {
    @GET("/members/friends/followings")
    fun getFollowingList(
        @Query("member_id") member_id: String,
        @Query("page") page: String,
        @Query("size") size: String
    ): Call<List<FollowingList>>

    @GET("/members/friends/followers")
    fun getFollowerList(
        @Query("member_id") member_id: String,
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

    @POST("/members/friends")
    fun addFriend(
        @Query("member_id") member_id: String
    ): Call<List<AddFriend>>

    @GET("/members/friends/search/follower")
    fun searchFollower(
        @Query("keyword")keyword: String
    ): Call<List<GlobalSearch>>

    @GET("/members/friends/search/following")
    fun searchFollowing(
        @Query("keyword")keyword: String
    ): Call<List<GlobalSearch>>
}