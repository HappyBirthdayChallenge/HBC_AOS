package com.inha.hbc.util

import android.util.Log
import com.inha.hbc.data.remote.req.BirthDateInfo
import com.inha.hbc.data.remote.req.NormSigninInfo
import com.inha.hbc.data.remote.resp.ErrorResp
import com.inha.hbc.data.remote.resp.NormSigninBody
import com.inha.hbc.data.remote.resp.kakaoSigninBody
import com.inha.hbc.ui.login.view.KakaoLoginView
import com.inha.hbc.ui.login.view.NormLoginView
import com.inha.hbc.ui.login.view.SetBirthView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create

class RetrofitService {
    lateinit var normLoginView: NormLoginView
    lateinit var kakaoLoginView: KakaoLoginView
    lateinit var setBirthView: SetBirthView

    fun normSignin(data: NormSigninInfo, view: NormLoginView) {
        val net = getRetrofit().create(RetroServiceInterface::class.java)
        normLoginView = view
        net.normalSignin(data).enqueue(object : Callback<NormSigninBody> {
            override fun onResponse(
                call: Call<NormSigninBody>,
                response: Response<NormSigninBody>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.code == "R-M011") {
                        normLoginView.onNormLoginSuccess(response.body()!!)
                    } else {
                        normLoginView.onNormLoginFailure(-100)
                    }
                } else {
                    val err = getErrorResponse(response.errorBody()!!)
                    normLoginView.onNormLoginFailure(err)
                }
            }

            override fun onFailure(call: Call<NormSigninBody>, t: Throwable) {
                t.message
                normLoginView.onNormLoginFailure(-1)
            }

        })
    }
    fun kakaoSignin(provider: String, token: String, viewData: KakaoLoginView) {
        val net = getRetrofit().create(RetroServiceInterface::class.java)
        kakaoLoginView = viewData

        net.kakaoSignin(provider, token).enqueue(object: Callback<kakaoSigninBody> {
            override fun onResponse(
                call: Call<kakaoSigninBody>,
                response: Response<kakaoSigninBody>
            ) {
                if (response.code() == 200) {
                    kakaoLoginView.onKakaoLoginSuccess()
                }
                else {
                    Log.d("retrofit", response.message().toString())
                    Log.d("retrofit", response.errorBody()!!.string())
                    kakaoLoginView.onKakaoLoginFailure(response.code())
                }
            }

            override fun onFailure(call: Call<kakaoSigninBody>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    fun setBirth(data: BirthDateInfo, view: SetBirthView) {
        val net = getRetrofit().create(RetroServiceInterface::class.java)
        setBirthView = view
        net.setBirth(data).enqueue(object : Callback<NormSigninBody>{
            override fun onResponse(
                call: Call<NormSigninBody>,
                response: Response<NormSigninBody>
            ) {
                Log.d("response", response.headers().toString())
                Log.d("response", response.errorBody()!!.string())
                Log.d("response", response.body().toString())
                if (response.code() == 200) {
                    setBirthView.onSetBirthSuccess(response.body()!!)
                }
                else {
                    setBirthView.onSetBirthFailure(response.code())
                }
            }

            override fun onFailure(call: Call<NormSigninBody>, t: Throwable) {
                setBirthView.onSetBirthFailure(-1)
            }

        })
    }
}