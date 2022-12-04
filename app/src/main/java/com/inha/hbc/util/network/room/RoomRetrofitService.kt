package com.inha.hbc.util.network.room

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.inha.hbc.data.remote.resp.message.CreateMessage
import com.inha.hbc.data.remote.resp.message.CreateMessageSuccess
import com.inha.hbc.data.remote.resp.message.SearchDeco
import com.inha.hbc.data.remote.resp.message.SearchDecoSuccess
import com.inha.hbc.data.remote.resp.room.GetReceiveMessage
import com.inha.hbc.data.remote.resp.room.GetReceiveMessageSuccess
import com.inha.hbc.ui.main.view.*
import com.inha.hbc.util.network.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomRetrofitService {
    fun callRetro(): RoomRetroServiceInterface {
        return NetworkModule.getRetrofit().create(RoomRetroServiceInterface::class.java)
    }

    fun errToJson(resp: String): JsonObject {
        val errBody = resp
        val suberr = errBody.substring(1 until  errBody.length - 1)
        return JsonParser.parseString(suberr).asJsonObject
    }

    lateinit var searchDecoView: SearchDecoView
    lateinit var getReceiveMessageView: GetReceiveMessageView
    lateinit var getNotifyView: GetNotifyView

    fun searchDeco(page: String, roomId: String, view: SearchDecoView) {
        searchDecoView = view
        callRetro().searchDeco(roomId, page).enqueue(object: Callback<List<SearchDeco>> {
            override fun onResponse(
                call: Call<List<SearchDeco>>,
                response: Response<List<SearchDeco>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as SearchDecoSuccess
                    if (resp.code == "R-R002") {
                        searchDecoView.onSearchDecoSuccess(resp)
                    }
                    else {
                        searchDecoView.onSearchDecoFailure()
                    }
                }
                else {
                    searchDecoView.onSearchDecoFailure()
                }
            }

            override fun onFailure(call: Call<List<SearchDeco>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    fun getReceiveMessage(page: String, roomId: String, size: String, view: GetReceiveMessageView) {
        getReceiveMessageView = view
        callRetro().GetReceiveMessage(roomId, page, size).enqueue(object: Callback<List<GetReceiveMessage>> {
            override fun onResponse(
                call: Call<List<GetReceiveMessage>>,
                response: Response<List<GetReceiveMessage>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as GetReceiveMessageSuccess
                    if (resp.code == "R-R003") {
                        getReceiveMessageView.onGetReceiveMessageSuccess(resp)
                    }
                    else {
                        getReceiveMessageView.onGetReceiveMessageFailure()
                    }
                }
                else {
                    getReceiveMessageView.onGetReceiveMessageFailure()
                }
            }

            override fun onFailure(call: Call<List<GetReceiveMessage>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getNotify(page: String, size: String, view: GetNotifyView) {
        getNotifyView = view
        callRetro().GetNotify(page, size).enqueue(object: Callback<List<GetNotify>>{
            override fun onResponse(
                call: Call<List<GetNotify>>,
                response: Response<List<GetNotify>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as GetNotifySuccess
                    if (resp.code == "R-AL001") {
                        getNotifyView.onGetNotifySuccess(resp)
                    }
                    else {
                        getNotifyView.onGetNotifyFailure()
                    }
                }
                else {
                    getNotifyView.onGetNotifyFailure()
                }
            }

            override fun onFailure(call: Call<List<GetNotify>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}