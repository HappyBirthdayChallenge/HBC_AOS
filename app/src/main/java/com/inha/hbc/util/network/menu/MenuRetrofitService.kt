package com.inha.hbc.util.network.menu

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.inha.hbc.data.remote.resp.menu.FollowerList
import com.inha.hbc.data.remote.resp.menu.FollowerListSuccess
import com.inha.hbc.data.remote.resp.menu.FollowingList
import com.inha.hbc.data.remote.resp.menu.FollowingListSuccess
import com.inha.hbc.ui.menu.view.FollowerListView
import com.inha.hbc.ui.menu.view.FollowingListView
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

    lateinit var followingListView: FollowingListView
    lateinit var followerListView: FollowerListView

    fun getFollowingList(page: String, size: String, view: FollowingListView) {
        followingListView = view
        callRetro().getFollowingList(page, size).enqueue(object: Callback<List<FollowingList>> {
            override fun onResponse(
                call: Call<List<FollowingList>>,
                response: Response<List<FollowingList>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as FollowingListSuccess
                    if (resp.code == "R-M014") {
                        followingListView.onFollowingListSuccess(resp)
                    }
                    else {
                        followingListView.onFollowingListFailure()
                    }
                }
                else {
                    followingListView.onFollowingListFailure()
                }
            }

            override fun onFailure(call: Call<List<FollowingList>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getFollowerList(page: String, size: String, view: FollowerListView) {
        followerListView = view
        callRetro().getFollowerList(page, size).enqueue(object: Callback<List<FollowerList>> {
            override fun onResponse(
                call: Call<List<FollowerList>>,
                response: Response<List<FollowerList>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as FollowerListSuccess
                    if (resp.code == "R-M020") {
                        followerListView.onFollowerListSuccess(resp)
                    }
                    else {
                        followerListView.onFollowerListFailure()
                    }
                }
                else {
                    followerListView.onFollowerListFailure()
                }
            }

            override fun onFailure(call: Call<List<FollowerList>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}