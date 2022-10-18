package com.inha.hbc.util

import com.google.gson.GsonBuilder
import com.inha.hbc.data.remote.resp.ErrorResp
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager


    const val BASE_URL = "http://ec2-3-36-103-165.ap-northeast-2.compute.amazonaws.com:8080"

    private var instance: Retrofit? = null
    private var gson = GsonBuilder().setLenient().create()

    fun getErrorResponse(errorBody: ResponseBody): ErrorResp {
        return instance!!.responseBodyConverter<ErrorResp>(
            ErrorResp::class.java,
            ErrorResp::class.java.annotations
        )!!.convert(errorBody)!!
    }

    fun getRetrofit(): Retrofit {
        if (instance == null) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor { chain: Interceptor.Chain ->
                    val origin = chain.request()
                    if (origin.url.encodedPath.equals("/members/accounts/birthday", true)) {
                        chain.proceed(origin.newBuilder().apply {
                            addHeader(
                                "Authorization",
                                "Bearer " + GlobalApplication.prefs.getRealAccessJwt().toString()
                            )
                        }.build())
                    } else {
                        chain.proceed(origin)
                    }
                }
                .build()

            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        return instance!!
    }