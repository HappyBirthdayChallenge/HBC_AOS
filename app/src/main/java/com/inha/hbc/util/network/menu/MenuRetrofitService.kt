package com.inha.hbc.util.network.menu

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.inha.hbc.data.remote.resp.menu.Friendlist
import com.inha.hbc.data.remote.resp.menu.FriendlistSuccess
import com.inha.hbc.ui.menu.view.FriendListView
import com.inha.hbc.util.network.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuRetrofitService {
    fun callRetro(): MenuRetroServiceInterface {
        return NetworkModule.getRetrofit().create(MenuRetroServiceInterface::class.java)
    }

    fun errToJson(resp: String): JsonObject {
        val errBody = resp
        val suberr = errBody.substring(1 until  errBody.length - 1)
        return JsonParser.parseString(suberr).asJsonObject
    }

    lateinit var friendListView: FriendListView

    fun friendList(page: String, size: String, view: FriendListView) {
        friendListView = view
        callRetro().getFriendList(page, size).enqueue(object: Callback<List<Friendlist>> {
            override fun onResponse(
                call: Call<List<Friendlist>>,
                response: Response<List<Friendlist>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as FriendlistSuccess
                    if (resp.code == "R-M014") {
                        friendListView.onFriendListSuccess(resp)
                    }
                    else {
                        friendListView.onFriendListFailure()
                    }
                }
                else {
                    friendListView.onFriendListFailure()
                }
            }

            override fun onFailure(call: Call<List<Friendlist>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}