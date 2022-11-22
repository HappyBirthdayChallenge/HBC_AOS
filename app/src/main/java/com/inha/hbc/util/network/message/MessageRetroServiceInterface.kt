package com.inha.hbc.util.network.message

import com.inha.hbc.data.remote.resp.message.CreateMessage
import com.inha.hbc.data.remote.resp.message.RoomInfo
import com.inha.hbc.data.remote.resp.message.Upload
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface MessageRetroServiceInterface {
    @POST("/messages/create")
    fun createMessage(
        @Query("room_id") roomId: String
    ): Call<List<CreateMessage>>

    @GET("/rooms")
    fun roomInfo(
        @Query("member_id") memeberId: String
    ): Call<List<RoomInfo>>

    @Multipart
    @POST("/files/upload/audio")
    fun audioUpload(
        @Part audio: MultipartBody.Part,
        @Part message_id: MultipartBody.Part
    ): Call<List<Upload>>
}