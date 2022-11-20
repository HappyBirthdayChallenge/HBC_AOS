package com.inha.hbc.util.network.menu

import com.inha.hbc.data.remote.resp.menu.Friendlist
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MenuRetroServiceInterface {
    @GET("/members/friends")
    fun getFriendList(
        @Query("page") page: String,
        @Query("size") size: String
    ): Call<List<Friendlist>>
}