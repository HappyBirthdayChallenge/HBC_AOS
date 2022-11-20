package com.inha.hbc.util.network.message

import com.inha.hbc.data.remote.resp.message.CreateMessage
import com.inha.hbc.data.remote.resp.message.RoomInfo
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface MessageRetroServiceInterface {
    @POST("/messages/create")
    fun createMessage(
        @Query("room_id") roomId: String
    ): Call<List<CreateMessage>>

    @POST("rooms")
    fun roomInfo(
        @Query("member_id") memeberId: String
    ): Call<List<RoomInfo>>
}