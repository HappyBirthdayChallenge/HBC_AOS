package com.inha.hbc.util.network.message

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.inha.hbc.data.remote.req.message.MessageData
import com.inha.hbc.data.remote.resp.message.*
import com.inha.hbc.ui.letter.view.UploadView
import com.inha.hbc.ui.letter.view.CreateMessageView
import com.inha.hbc.ui.letter.view.MessageLikeView
import com.inha.hbc.ui.letter.view.UploadMessageView
import com.inha.hbc.ui.main.view.GetMessageView
import com.inha.hbc.ui.main.view.RoomInfoView
import com.inha.hbc.util.network.NetworkModule
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class MessageRetrofitService {
    fun callRetro(): MessageRetroServiceInterface {
        return NetworkModule.getRetrofit().create(MessageRetroServiceInterface::class.java)
    }

    fun errToJson(resp: String): JsonObject {
        val errBody = resp
        val suberr = errBody.substring(1 until  errBody.length - 1)
        return JsonParser.parseString(suberr).asJsonObject
    }

    lateinit var createMessageView: CreateMessageView
    lateinit var roomInfoView: RoomInfoView
    lateinit var uploadView: UploadView
    lateinit var uploadMessageView: UploadMessageView
    lateinit var getMessageView: GetMessageView
    lateinit var messagelLikeView: MessageLikeView

    fun createMessage(id: String, view: CreateMessageView) {
        createMessageView = view
        callRetro().createMessage(id).enqueue(object: Callback<List<CreateMessage>> {
            override fun onResponse(
                call: Call<List<CreateMessage>>,
                response: Response<List<CreateMessage>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as CreateMessageSuccess
                    if (resp.code == "R-RM002") {
                        createMessageView.onCreateMessageSuccess(resp)
                    }
                    else {
                        createMessageView.onCreateMessageFailure()
                    }
                }
                else {
                    createMessageView.onCreateMessageFailure()
                }
            }

            override fun onFailure(call: Call<List<CreateMessage>>, t: Throwable) {
                createMessageView.onCreateMessageFailure()
            }

        })
    }

    fun audioUpload(path: String, messageId: Int, clientId: Int, view: UploadView) {
        uploadView = view

        val mp = MultipartBody.Part.createFormData("audio", File(path).name + ".m4a",
            File(path).asRequestBody("audio/m4a".toMediaTypeOrNull())
        )
        val id = messageId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val clId = clientId.toString().toRequestBody("text/plain".toMediaTypeOrNull())


        callRetro().audioUpload(mp, id, clId).enqueue(object: Callback<List<Upload>> {
            override fun onResponse(call: Call<List<Upload>>, response: Response<List<Upload>>) {if (response.isSuccessful) {
                val resp = response.body()!![0] as UploadSuccess
                if (resp.code == "R-FI003") {
                    uploadView.onUploadSuccess(resp)
                }
                else {
                    uploadView.onUploadFailure()
                }
            }
            else {
                uploadView.onUploadFailure()
            }
            }

            override fun onFailure(call: Call<List<Upload>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    fun imgUpload(path: String, messageId: Int, clientId: Int, view: UploadView) {
        uploadView = view

        val mp = MultipartBody.Part.createFormData("image", File(path).name,
            File(path).asRequestBody("image/*".toMediaTypeOrNull())
        )
        val id = messageId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val clId = clientId.toString().toRequestBody("text/plain".toMediaTypeOrNull())


        callRetro().imgUpload(mp, id, clId).enqueue(object: Callback<List<Upload>> {
            override fun onResponse(call: Call<List<Upload>>, response: Response<List<Upload>>) {if (response.isSuccessful) {
                val resp = response.body()!![0] as UploadSuccess
                if (resp.code == "R-FI001") {
                    uploadView.onUploadSuccess(resp)
                }
                else {
                    uploadView.onUploadFailure()
                }
            }
            else {
                uploadView.onUploadFailure()
            }
            }

            override fun onFailure(call: Call<List<Upload>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    fun videoUpload(path: String, messageId: Int, clientId: Int, view: UploadView) {
        uploadView = view

        val mp = MultipartBody.Part.createFormData("video", File(path).name,
            File(path).asRequestBody("video/*".toMediaTypeOrNull())
        )
        val id = messageId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val clId = clientId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        callRetro().videoUpload(mp, id, clId).enqueue(object: Callback<List<Upload>> {
            override fun onResponse(call: Call<List<Upload>>, response: Response<List<Upload>>) {if (response.isSuccessful) {
                val resp = response.body()!![0] as UploadSuccess
                if (resp.code == "R-FI002") {
                    uploadView.onUploadSuccess(resp)
                }
                else {
                    uploadView.onUploadFailure()
                }
            }
            else {
                uploadView.onUploadFailure()
            }
            }

            override fun onFailure(call: Call<List<Upload>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    fun uploadMessage(data: MessageData, view: UploadMessageView) {
        uploadMessageView = view
        callRetro().messageUpload(data).enqueue(object : Callback<List<UploadMessage>>{
            override fun onResponse(
                call: Call<List<UploadMessage>>,
                response: Response<List<UploadMessage>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as UploadMessageSuccess
                    if (resp.code == "R-RM001") {
                        uploadMessageView.onUploadMessageSuccess(resp)
                    }
                    else {
                        uploadMessageView.onUploadMessageFailure()
                    }
                }
                else {
                    uploadMessageView.onUploadMessageFailure()
                }
            }

            override fun onFailure(call: Call<List<UploadMessage>>, t: Throwable) {
            }
        })
    }

    fun roomInfo(memberId: String, view: RoomInfoView) {
        roomInfoView = view
        callRetro().roomInfo(memberId).enqueue(object: Callback<List<RoomInfo>> {
            override fun onResponse(
                call: Call<List<RoomInfo>>,
                response: Response<List<RoomInfo>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as RoomInfoSuccess
                    if (resp.code == "R-R001") {
                        roomInfoView.onRoomInfoSuccess(resp)
                    }
                    else {
                        roomInfoView.onRoomInfoFailure()
                    }
                }
                else {
                    roomInfoView.onRoomInfoFailure()
                }
            }

            override fun onFailure(call: Call<List<RoomInfo>>, t: Throwable) {
                roomInfoView.onRoomInfoFailure()
            }

        })
    }


    fun getMessage(messageId: String, view: GetMessageView) {
        getMessageView = view
        callRetro().getMessage(messageId).enqueue(object: Callback<List<GetMessage>>{
            override fun onResponse(
                call: Call<List<GetMessage>>,
                response: Response<List<GetMessage>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as GetMessageSuccess
                    if (resp.code == "R-RM003") {
                        getMessageView.onGetMessageSuccess(resp)
                    }
                    else {
                        getMessageView.onGetMessageFailure()
                    }
                }
                else {
                    getMessageView.onGetMessageFailure()
                }
            }

            override fun onFailure(call: Call<List<GetMessage>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    fun messageLike(messageId: String, view: MessageLikeView) {
        messagelLikeView = view
        callRetro().messageLike(messageId).enqueue(object: Callback<List<MessageLike>> {
            override fun onResponse(
                call: Call<List<MessageLike>>,
                response: Response<List<MessageLike>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as MessageLikeSuccess
                    if (resp.code == "R-RM009") {
                        messagelLikeView.onMessageLikeSuccess()
                    }
                    else {
                        messagelLikeView.onMessageLikeFailure()
                    }
                }
                else {
                    messagelLikeView.onMessageLikeFailure()
                }
            }

            override fun onFailure(call: Call<List<MessageLike>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}