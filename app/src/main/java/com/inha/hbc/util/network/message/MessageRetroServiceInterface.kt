package com.inha.hbc.util.network.message

import com.inha.hbc.data.remote.req.message.MessageData
import com.inha.hbc.data.remote.resp.message.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
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
        @Part ("message_id") message_id: RequestBody,
        @Part ("client_id") client_id: RequestBody
    ): Call<List<Upload>>

    @Multipart
    @POST("/files/upload/image")
    fun imgUpload(
        @Part img: MultipartBody.Part,
        @Part ("message_id") message_id: RequestBody,
        @Part ("client_id") client_id: RequestBody
    ): Call<List<Upload>>

    @Multipart
    @POST("/files/upload/video")
    fun videoUpload(
        @Part video: MultipartBody.Part,
        @Part ("message_id") message_id: RequestBody,
        @Part ("client_id") client_id: RequestBody
    ): Call<List<Upload>>

    @POST("/messages/upload")
    fun messageUpload(
        @Body request: MessageData
    ): Call<List<UploadMessage>>

    @GET("/messages/{message_id}")
    fun getMessage(
        @Path("message_id") message_id: String
    ): Call<List<GetMessage>>

    @PATCH("/messages/{message_id}/like")
    fun messageLike(
        @Path("message_id") message_id: String
    ):Call<List<MessageLike>>
}