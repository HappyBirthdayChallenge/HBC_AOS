package com.inha.hbc.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import javax.inject.Inject


object NetworkModule {
    const val BASE_URL = "http://ec2-3-36-103-165.ap-northeast-2.compute.amazonaws.com:8080"

    private var instance: Retrofit? = null
    private var gson = GsonBuilder().setLenient().create()
    private var converter =
        Json.asConverterFactory("application/json".toMediaType())

    fun getRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY


        if (instance == null) {
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
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
                .addInterceptor(RespInterceptor())
                .build()

            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JsonConverterFactory(GsonConverterFactory.create(gson), converter))
                .client(client)
                .build()
        }

        return instance!!
    }

    class JsonConverterFactory @Inject constructor(
        private val gsonConverterFactory: GsonConverterFactory,
        private val kotlinSerializationConverterFactory: Converter.Factory
    ) : Converter.Factory() {
        override fun requestBodyConverter(
            type: Type,
            parameterAnnotations: Array<out Annotation>,
            methodAnnotations: Array<out Annotation>,
            retrofit: Retrofit
        ): Converter<*, RequestBody>? {
            return gsonConverterFactory
                .requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
        }

        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ): Converter<ResponseBody, *>? {
            return when {
                annotations.any() { it.annotationClass == Gson::class } -> {
                    gsonConverterFactory.responseBodyConverter(type, annotations, retrofit)
                }
                else -> {
                    kotlinSerializationConverterFactory
                        .responseBodyConverter(type, annotations, retrofit)
                }
            }
        }
    }
}