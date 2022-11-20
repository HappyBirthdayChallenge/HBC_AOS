package com.inha.hbc.util.network.message

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.inha.hbc.data.remote.resp.CheckTokenSuccess
import com.inha.hbc.data.remote.resp.message.CreateMessage
import com.inha.hbc.data.remote.resp.message.CreateMessageSuccess
import com.inha.hbc.data.remote.resp.message.RoomInfo
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.ui.letter.view.CreateMessageView
import com.inha.hbc.ui.letter.view.RoomInfoView
import com.inha.hbc.util.network.NetworkModule
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
                        createMessageView.onCreateMessageSuccess()
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
                        roomInfoView.onRoomInfoSuccess()
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