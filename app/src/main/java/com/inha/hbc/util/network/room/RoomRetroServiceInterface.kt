package com.inha.hbc.util.network.room

import com.inha.hbc.data.remote.resp.message.SearchDeco
import com.inha.hbc.data.remote.resp.room.GetReceiveMessage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RoomRetroServiceInterface {
    @GET("/rooms/{room_id}/decorations")
    fun searchDeco(
        @Path("room_id") room_id: String,
        @Query("page") page: String
    ):Call<List<SearchDeco>>

    @GET("/rooms/{room_id}/messages")
    fun GetReceiveMessage(
        @Path("room_id") room_id: String,
        @Query("page") page: String,
        @Query("size") size: String
    ): Call<List<GetReceiveMessage>>
}