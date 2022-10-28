package com.inha.hbc.util

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject

class RespInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val response = if (origin.url.encodedPath.equals("/members/accounts/birthday", true)) {
            chain.proceed(origin.newBuilder().apply {
                addHeader(
                    "Authorization",
                    "Bearer " + GlobalApplication.prefs.getRealAccessJwt().toString()
                )
            }.build())
        }
        else {
            chain.proceed(origin)
        }

        val respJson = response.Json()
        val code = respJson["code"]
        val type = code.toString()[0]
        respJson.put("type", type)
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