package com.inha.hbc.util

import android.util.Log
import com.inha.hbc.data.remote.req.NormSigninInfo
import com.inha.hbc.data.remote.resp.NormSigninBody
import com.inha.hbc.ui.login.view.NormLoginView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitService {

    lateinit var normLoginView: NormLoginView

    fun normSignin(data: NormSigninInfo, view: NormLoginView) {
        val normAuth = getRetrofit().create(RetroServiceInterface::class.java)
        normLoginView = view
        normAuth.normalSignin(data).enqueue(object : Callback<NormSigninBody> {
            override fun onResponse(call: Call<NormSigninBody>, response: Response<NormSigninBody>) {
                when(response.code()) {
                    200 -> {
                        if (response.body()!!.code == "R-M011") {
                            normLoginView.onNormLoginSuccess()
                        }
                        else {
                            normLoginView.onNormLoginFailure(response.code())
                        }
                    }
                    else -> {
                        normLoginView.onNormLoginFailure(response.code())
                    }
                }
                Log.d("caller", response.toString())
            }

            override fun onFailure(call: Call<NormSigninBody>, t: Throwable) {
                Log.d("caller", call.toString())
            }

        })
    }
}