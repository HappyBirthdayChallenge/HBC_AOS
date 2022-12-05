package com.inha.hbc.util.network.main

import com.inha.hbc.data.remote.resp.main.GlobalSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MainRetrofitServiceInterface {
    @GET("/members/accounts/search")
    fun globalSearch(
        @Query("keyword") keyword: String
    ):Call<List<GlobalSearch>>
}