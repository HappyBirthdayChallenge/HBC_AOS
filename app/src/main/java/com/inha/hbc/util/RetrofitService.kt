package com.inha.hbc.util

import android.util.Log
import com.inha.hbc.data.remote.req.CheckCodeData
import com.inha.hbc.data.remote.req.CheckPhoneData
import com.inha.hbc.data.remote.req.NormSigninInfo
import com.inha.hbc.data.remote.resp.*
import com.inha.hbc.ui.login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import kotlin.reflect.typeOf

class RetrofitService {
    lateinit var normLoginView: NormLoginView
    lateinit var kakaoLoginView: KakaoLoginView
    lateinit var checkIdView: CheckIdView
    lateinit var checkPhoneView: CheckPhoneView
    lateinit var checkCodeView: CheckCodeView
    lateinit var sendCodeView: SendCodeView
    lateinit var setBirthView: SetBirthView

    fun callRetro(): RetroServiceInterface  {
        return NetworkModule.getRetrofit().create(RetroServiceInterface::class.java)
    }


    fun normSignin(data: NormSigninInfo, view: NormLoginView) {
        val normAuth = callRetro()
        normLoginView = view
        normAuth.normalSignin(data).enqueue(object : Callback<List<NormSignin>> {
            override fun onResponse(call: Call<List<NormSignin>>, response: Response<List<NormSignin>>) {
                Log.d("respNorm", response.body().toString())
                Log.d("respErrbody", response.code().toString())
//                when(response.code()) {
//                    200 -> {
//                        if (response.body() == "R-M011") {
//                            Log.d("signinResp", response.body().toString())
//                            normLoginView.onNormLoginSuccess(response.body()!!)
//                        }
//                        else {
//                            normLoginView.onNormLoginFailure(response.code())
//                        }
//                    }
//                    else -> {
//                        normLoginView.onNormLoginFailure(response.code())
//                    }
//                }
            }

            override fun onFailure(call: Call<List<NormSignin>>, t: Throwable) {
                Log.d("respERrNorm", t.message.toString())
                Log.d("respErrNorm", call.cancel().toString())
                normLoginView.onNormLoginFailure(10000)
            }

        })
    }
    fun kakaoSignin(provider: String, token: String, viewData: KakaoLoginView) {
        val kakaoAuth = NetworkModule.getRetrofit().create(RetroServiceInterface::class.java)

        val net = NetworkModule.getRetrofit().create(RetroServiceInterface::class.java)
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

    fun checkId(data: String, view: CheckIdView) {
        checkIdView = view

        callRetro().checkId(data).enqueue(object : Callback<List<CheckId>> {
            override fun onResponse(call: Call<List<CheckId>>, response: Response<List<CheckId>>) {
                if (response.isSuccessful) {
                    if (response.body()!![0].javaClass.simpleName == "IdValid") {
                        checkIdView.onResponseSuccess()
                    } else {
                        checkIdView.onResponseFailure()
                    }
                }
            }

            override fun onFailure(call: Call<List<CheckId>>, t: Throwable) {
                Log.d("idRespERr", t.toString())
            }

        })
    }

    fun checkPhone(data: String, view: CheckPhoneView) {
        checkPhoneView = view
        val body = CheckPhoneData(data, "SIGNUP")
        callRetro().checkPhone(body).enqueue(object : Callback<List<CheckPhone>> {
            override fun onResponse(
                call: Call<List<CheckPhone>>,
                response: Response<List<CheckPhone>>
            ) {
                if (response.isSuccessful) {
                    if ((response.body()!! as PhoneSuccess).code == "R-M015") {
                        checkPhoneView.onResponseSuccess()
                    }
                    else {
                        checkPhoneView.onResponseFailure()
                    }
                }
                else {
                    (response.errorBody()!! as PhoneFailure).errors!!.reason
                }
            }

            override fun onFailure(call: Call<List<CheckPhone>>, t: Throwable) {
            }

        })
    }

    fun reqCode(data: String, view: SendCodeView) {
        sendCodeView = view
        val body = CheckPhoneData(data, "SIGNUP")

        callRetro().reqCode(body).enqueue(object: Callback<List<CheckPhone>> {
            override fun onResponse(
                call: Call<List<CheckPhone>>,
                response: Response<List<CheckPhone>>
            ) {
                if (response.isSuccessful) {
                    if ((response.body()!! as PhoneSuccess).code == "R-IV004") {
                        sendCodeView.onSendCodeSuccess()
                    }
                    else {
                        sendCodeView.onSendCodeFailure()
                    }
                }
            }

            override fun onFailure(call: Call<List<CheckPhone>>, t: Throwable) {
            }

        })
    }

    fun checkCode(data: CheckCodeData, view: CheckCodeView) {
        checkCodeView = view
        callRetro().checkCode(data).enqueue(object : Callback<List<CheckPhone>> {
            override fun onResponse(
                call: Call<List<CheckPhone>>,
                response: Response<List<CheckPhone>>
            ) {
                if (response.isSuccessful) {
                    if ((response.body()!! as PhoneSuccess).code == "R-IV004") {
                        checkCodeView.onCheckCodeResponseSuccess()
                    }
                    else {
                        checkCodeView.onCheckCodeResponseFailure()
                    }
                }
            }

            override fun onFailure(call: Call<List<CheckPhone>>, t: Throwable) {
            }

        })
    }
//    fun setBirth(data: BirthDateInfo, view: SetBirthView) {
//        val birth = getRetrofit().create(RetroServiceInterface::class.java)
//        setBirthView = view
//
//
//        birth.setBirth(data).enqueue(object : Callback<NormSigninBody>{
//            override fun onResponse(
//                call: Call<NormSigninBody>,
//                response: Response<NormSigninBody>
//            ) {
//                Log.d("response", response.body().toString())
//                if (response.code() == 200) {
//                    setBirthView.onSetBirthSuccess(response.body()!!)
//                }
//                else {
//                    setBirthView.onSetBirthFailure(response.code())
//                }
//            }
//
//            override fun onFailure(call: Call<NormSigninBody>, t: Throwable) {
//                setBirthView.onSetBirthFailure(-1)
//            }
//
//        })
//    }
}