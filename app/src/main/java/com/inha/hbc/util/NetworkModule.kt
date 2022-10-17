package com.inha.hbc.util

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager

const val BASE_URL = "http://ec2-3-36-103-165.ap-northeast-2.compute.amazonaws.com:8080"

private var instance: Retrofit? = null
private var gson = GsonBuilder().setLenient().create()


fun getRetrofit(): Retrofit {
    if (instance == null) {

    val client = OkHttpClient.Builder()
        .cookieJar(JavaNetCookieJar(CookieManager()))
        .addInterceptor{
            chain: Interceptor.Chain ->
            val origin = chain.request()
            if (origin.url.encodedPath.equals( "/associates/birthday", true)) {
                chain.proceed(origin.newBuilder().apply{
                    addHeader("Authorization", "Bearer " + GlobalApplication.prefs.getRealAccessJwt().toString())
                }.build())
            }
            else {
                chain.proceed(origin)
            }
        }
        .build()

    instance = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
}

    return instance!!
}