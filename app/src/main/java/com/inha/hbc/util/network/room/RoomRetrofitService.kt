package com.inha.hbc.util.network.room

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.inha.hbc.data.remote.resp.message.CreateMessage
import com.inha.hbc.data.remote.resp.message.CreateMessageSuccess
import com.inha.hbc.data.remote.resp.message.SearchDeco
import com.inha.hbc.data.remote.resp.message.SearchDecoSuccess
import com.inha.hbc.ui.main.view.SearchDecoView
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
}