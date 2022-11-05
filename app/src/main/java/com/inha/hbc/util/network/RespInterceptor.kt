package com.inha.hbc.util.network

import com.inha.hbc.util.sharedpreference.GlobalApplication
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

        if (respJson.has("data")) {
            val data = respJson["data"]
            if (data == "") {
                if (origin.url.encodedPath.equals("/auth/verify/code", true)) {
                    val dt = JSONObject()
                    dt.put("key", "")
                    respJson.put("data", dt)
                }

                else if (origin.url.encodedPath.equals("/auth/signin", true)) {
                    val dt = JSONObject()
                    dt.put("access_token", "")
                    dt.put("refresh_token", "")
                    respJson.put("data", dt)
                }
            }
        }
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