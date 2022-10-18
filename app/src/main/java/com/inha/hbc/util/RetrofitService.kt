package com.inha.hbc.util

import android.util.Log
import com.inha.hbc.data.remote.req.BirthDateInfo
import com.inha.hbc.data.remote.req.NormSigninInfo
import com.inha.hbc.data.remote.resp.KakaoSigninBody
import com.inha.hbc.data.remote.resp.NormSigninBody
import com.inha.hbc.ui.login.view.KakaoLoginView
import com.inha.hbc.ui.login.view.NormLoginView
import com.inha.hbc.ui.login.view.SetBirthView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class RetrofitService {

    lateinit var normLoginView: NormLoginView
    lateinit var kakaoLoginView: KakaoLoginView
    lateinit var setBirthView: SetBirthView

    fun normSignin(data: NormSigninInfo, view: NormLoginView) {
        val normAuth = getRetrofit().create(RetroServiceInterface::class.java)
        normLoginView = view
        normAuth.normalSignin(data).enqueue(object : Callback<NormSigninBody> {
            override fun onResponse(call: Call<NormSigninBody>, response: Response<NormSigninBody>) {
                when(response.code()) {
                    200 -> {
                        if (response.body()!!.code == "R-M011") {
                            Log.d("signinResp", response.body().toString())
                            normLoginView.onNormLoginSuccess(response.body()!!)
                        }
                        else {
                            normLoginView.onNormLoginFailure(response.code())
                        }
                    }
                    else -> {
                        normLoginView.onNormLoginFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<NormSigninBody>, t: Throwable) {
                normLoginView.onNormLoginFailure(10000)
            }

        })
    }
    fun kakaoSignin(provider: String, token: String, viewData: KakaoLoginView) {
        val kakaoAuth = getRetrofit().create(RetroServiceInterface::class.java)

        kakaoLoginView = viewData

        kakaoAuth.kakaoSignin(provider, token).enqueue(object: Callback<KakaoSigninBody> {
            override fun onResponse(
                call: Call<KakaoSigninBody>,
                response: Response<KakaoSigninBody>
            ) {
                if (response.code() == 200) {
                    kakaoLoginView.onKakaoLoginSuccess()
                }
                else {
                    Log.d("retrofit", response.toString())
                    kakaoLoginView.onKakaoLoginFailure(response.code())
                }
            }

            override fun onFailure(call: Call<KakaoSigninBody>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    fun setBirth(data: BirthDateInfo, view: SetBirthView) {
        val birth = getRetrofit().create(RetroServiceInterface::class.java)
        setBirthView = view


        birth.setBirth(data).enqueue(object : Callback<NormSigninBody>{
            override fun onResponse(
                call: Call<NormSigninBody>,
                response: Response<NormSigninBody>
            ) {
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