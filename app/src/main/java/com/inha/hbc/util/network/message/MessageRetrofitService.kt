package com.inha.hbc.util.network.message

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.inha.hbc.data.remote.resp.message.*
import com.inha.hbc.ui.letter.view.UploadView
import com.inha.hbc.ui.letter.view.CreateMessageView
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

    fun audioUpload(path: String, messageId: Int, view: UploadView) {
        uploadView = view

        val mp = MultipartBody.Part.createFormData("audio", File(path).name + ".m4a",
            File(path).asRequestBody("audio/m4a".toMediaTypeOrNull())
        )
        val id = messageId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        callRetro().audioUpload(mp, id).enqueue(object: Callback<List<Upload>> {
            override fun onResponse(call: Call<List<Upload>>, response: Response<List<Upload>>) {if (response.isSuccessful) {
                val resp = response.body()!![0] as UploadSuccess
                if (resp.code == "R-FI003") {
                    uploadView.onAudioUploadSuccess(resp)
                }
                else {
                    uploadView.onAudioUploadFailure()
                }
            }
            else {
                uploadView.onAudioUploadFailure()
            }
            }

            override fun onFailure(call: Call<List<Upload>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    fun imgUpload(path: String, messageId: Int, view: UploadView) {
        uploadView = view

        val mp = MultipartBody.Part.createFormData("image", File(path).name,
            File(path).asRequestBody("image/*".toMediaTypeOrNull())
        )
        val id = messageId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        callRetro().audioUpload(mp, id).enqueue(object: Callback<List<Upload>> {
            override fun onResponse(call: Call<List<Upload>>, response: Response<List<Upload>>) {if (response.isSuccessful) {
                val resp = response.body()!![0] as UploadSuccess
                if (resp.code == "R-FI001") {
                    uploadView.onAudioUploadSuccess(resp)
                }
                else {
                    uploadView.onAudioUploadFailure()
                }
            }
            else {
                uploadView.onAudioUploadFailure()
            }
            }

            override fun onFailure(call: Call<List<Upload>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    fun videoUpload(path: String, messageId: Int, view: UploadView) {
        uploadView = view

        val mp = MultipartBody.Part.createFormData("video", File(path).name,
            File(path).asRequestBody("video/*".toMediaTypeOrNull())
        )
        val id = messageId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        callRetro().audioUpload(mp, id).enqueue(object: Callback<List<Upload>> {
            override fun onResponse(call: Call<List<Upload>>, response: Response<List<Upload>>) {if (response.isSuccessful) {
                val resp = response.body()!![0] as UploadSuccess
                if (resp.code == "R-FI003") {
                    uploadView.onAudioUploadSuccess(resp)
                }
                else {
                    uploadView.onAudioUploadFailure()
                }
            }
            else {
                uploadView.onAudioUploadFailure()
            }
            }

            override fun onFailure(call: Call<List<Upload>>, t: Throwable) {
                TODO("Not yet implemented")
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


}