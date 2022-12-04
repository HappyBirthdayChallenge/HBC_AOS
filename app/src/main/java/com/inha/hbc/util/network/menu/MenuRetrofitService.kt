package com.inha.hbc.util.network.menu

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.inha.hbc.data.remote.resp.menu.*
import com.inha.hbc.ui.menu.view.*
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
    lateinit var getProfileView: GetProfileView
    lateinit var getMymessageView: GetMymessageView
    lateinit var addFriendView: AddFriendView

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

    fun getProfile(memberId: String, view: GetProfileView) {
        getProfileView = view
        callRetro().getProfile(memberId).enqueue(object: Callback<List<GetProfile>>{
            override fun onResponse(
                call: Call<List<GetProfile>>,
                response: Response<List<GetProfile>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as GetProfileSuccess
                    if (resp.code == "R-M022") {
                        getProfileView.onGetProfileSuccess(resp)
                    }
                    else {
                        getProfileView.onGetProfileFailure()
                    }
                }
                else {
                    getProfileView.onGetProfileFailure()
                }
            }

            override fun onFailure(call: Call<List<GetProfile>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getMymessage(page: String, size: String, view: GetMymessageView) {
        getMymessageView = view
        callRetro().getMymessage(page, size).enqueue(object: Callback<List<GetMymessage>> {
            override fun onResponse(
                call: Call<List<GetMymessage>>,
                response: Response<List<GetMymessage>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as GetMymessageSuccess
                    if (resp.code == "R-RM007") {
                        getMymessageView.onGetMymessageSuccess(resp)
                    }
                    else {
                        getMymessageView.onGetMymessageFailure()
                    }
                }
                else {
                    getMymessageView.onGetMymessageFailure()
                }
            }

            override fun onFailure(call: Call<List<GetMymessage>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addFriend(memId: String, view: AddFriendView) {
        addFriendView = view
        callRetro().addFriend(memId).enqueue(object: Callback<List<AddFriend>>{
            override fun onResponse(
                call: Call<List<AddFriend>>,
                response: Response<List<AddFriend>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as AddFriendSuccess
                    if (resp.code == "R-M013") {
                        addFriendView.onAddFriendSuccess()
                    }
                    else {
                        addFriendView.onAddFriendFailure()
                    }
                }
                else {
                    addFriendView.onAddFriendFailure()
                }
            }

            override fun onFailure(call: Call<List<AddFriend>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}