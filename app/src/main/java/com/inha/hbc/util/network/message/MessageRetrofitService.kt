package com.inha.hbc.util.network.message

import android.net.Uri
import androidx.core.net.toFile
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.inha.hbc.data.remote.resp.message.*
import com.inha.hbc.ui.letter.view.AudioUploadView
import com.inha.hbc.ui.letter.view.CreateMessageView
import com.inha.hbc.ui.main.view.RoomInfoView
import com.inha.hbc.util.fragmentmanager.LetterFragmentManager
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.network.NetworkModule
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    lateinit var audioUploadView: AudioUploadView

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

    fun audioUpload(uri: Uri, messageId: Int, view: AudioUploadView) {
        audioUploadView = view
        val mp = MultipartBody.Part.create(uri.toFile().asRequestBody("audio/*".toMediaType()))
        val id = MultipartBody.Part.create(messageId.toString().toRequestBody())
        callRetro().audioUpload(mp, id).enqueue(object: Callback<List<Upload>> {
            override fun onResponse(call: Call<List<Upload>>, response: Response<List<Upload>>) {if (response.isSuccessful) {
                val resp = response.body()!![0] as UploadSuccess
                if (resp.code == "R-M001") {
                    audioUploadView.onAudioUploadSuccess(resp)
                }
                else {
                    audioUploadView.onAudioUploadFailure()
                }
            }
            else {
                audioUploadView.onAudioUploadFailure()
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