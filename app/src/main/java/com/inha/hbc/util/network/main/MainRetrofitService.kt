package com.inha.hbc.util.network.main

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.inha.hbc.data.remote.resp.main.GlobalSearch
import com.inha.hbc.data.remote.resp.main.GlobalSearchSuccess
import com.inha.hbc.data.remote.resp.menu.FollowingListSuccess
import com.inha.hbc.ui.main.view.GlobalSearchView
import com.inha.hbc.util.network.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRetrofitService {
    fun callRetro(): MainRetrofitServiceInterface {
        return NetworkModule.getRetrofit().create(MainRetrofitServiceInterface::class.java)
    }

    fun errToJson(resp: String): JsonObject {
        val errBody = resp
        val suberr = errBody.substring(1 until  errBody.length - 1)
        return JsonParser.parseString(suberr).asJsonObject
    }

    lateinit var globalSearchView: GlobalSearchView

    fun globalSearch(keyword:String, view: GlobalSearchView) {
        globalSearchView = view
        callRetro().globalSearch(keyword).enqueue(object : Callback<List<GlobalSearch>> {
            override fun onResponse(
                call: Call<List<GlobalSearch>>,
                response: Response<List<GlobalSearch>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as GlobalSearchSuccess
                    if (resp.code == "R-M023") {
                        globalSearchView.onGlobalSearchSuccess(resp)
                    }
                    else {
                        globalSearchView.onGlobalSearchFailure()
                    }
                }
                else {
                    globalSearchView.onGlobalSearchFailure()
                }
            }

            override fun onFailure(call: Call<List<GlobalSearch>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}