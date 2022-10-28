package com.inha.hbc.util

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject

class RespInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val respJson = response.Json()
        val a = ArrayList<JSONObject>()
        a.add(respJson)
        val b = a.toString()

        return response.newBuilder()
            .body(b.toResponseBody())
            .build()
    }
    fun Response.Json(): JSONObject {
        val jsonString = this.body?.string()
        return JSONObject(jsonString)
    }

}