package com.inha.hbc.util

import android.util.Log
import com.inha.hbc.data.remote.req.*
import com.inha.hbc.data.remote.resp.*
import com.inha.hbc.ui.login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitService {
    lateinit var normLoginView: NormLoginView
    lateinit var kakaoLoginView: KakaoLoginView
    lateinit var checkIdView: CheckIdView
    lateinit var checkPhoneView: CheckPhoneView
    lateinit var checkCodeView: CheckCodeView
    lateinit var sendCodeView: SendCodeView
    lateinit var checkBirthView: CheckBirthView
    lateinit var getSignupView: GetSignupView

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
    fun kakaoSignin(provider: String, token: String, view: KakaoLoginView) {
        kakaoLoginView = view

        callRetro().kakaoSignin(provider, token).enqueue(object: Callback<List<KakaoSignin>> {
            override fun onResponse(
                call: Call<List<KakaoSignin>>,
                response: Response<List<KakaoSignin>>
            ) {
                if (response.isSuccessful) {
                    if ((response.body()!![0] as KakaoSuccess).code == "R-M011") {
                        kakaoLoginView.onKakaoLoginSuccess((response.body()!![0] as KakaoSuccess).data)
                    }
                    else {
                        kakaoLoginView.onKakaoLoginFailure(response.code())
                    }
                }
                else {
                    kakaoLoginView.onKakaoLoginFailure(response.code())
                }
            }

            override fun onFailure(call: Call<List<KakaoSignin>>, t: Throwable) {
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
                    if ((response.body()!![0] as PhoneSuccess).code == "R-M015") {
                        checkPhoneView.onResponseSuccess()
                    }
                    else {
                        checkPhoneView.onResponseFailure((response.body()!![0] as PhoneSuccess))
                    }
                }
                else {
                    Log.d("failresp", response.body().toString())
                    Log.d("failresp", response.errorBody().toString())
                    checkPhoneView.onResponseFailure((response.errorBody() as PhoneFailure))
                }
            }

            override fun onFailure(call: Call<List<CheckPhone>>, t: Throwable) {
                checkPhoneView.onResponseFailure()
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
                    if ((response.body()!![0] as PhoneSuccess).code == "R-IV004") {
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
        callRetro().checkCode(data).enqueue(object : Callback<List<CheckCode>> {
            override fun onResponse(
                call: Call<List<CheckCode>>,
                response: Response<List<CheckCode>>
            ) {
                if (response.isSuccessful) {
                    if ((response.body()!![0] as CodeSuccess).code == "R-IV001") {
                        checkCodeView.onCheckCodeResponseSuccess(response.body()!![0] as CodeSuccess)
                    }
                    else {
                        checkCodeView.onCheckCodeResponseFailure(response.body()!![0] as CodeSuccess)
                    }
                }
                else {
                    checkCodeView.onCheckCodeResponseFailure(response.body()!![0] as CodeFailure)
                }
            }

            override fun onFailure(call: Call<List<CheckCode>>, t: Throwable) {

            }

        })
    }

    fun getSignup(data: GetSignupData, view: GetSignupView) {
        getSignupView = view
        callRetro().getSignup(data).enqueue(object: Callback<List<GetSignup>>{
            override fun onResponse(
                call: Call<List<GetSignup>>,
                response: Response<List<GetSignup>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as SignupSuccess
                    getSignupView.onSignupSuccess()
                }
                Log.d("checkREsp", response.toString())
            }

            override fun onFailure(call: Call<List<GetSignup>>, t: Throwable) {
                Log.d("checkREsp", t.toString())
            }

        })
    }

    fun checkBirth(data: CheckBirthData, view: CheckBirthView) {
        checkBirthView = view

        callRetro().checkBirth(data).enqueue(object : Callback<List<CheckBirth>> {
            override fun onResponse(
                call: Call<List<CheckBirth>>,
                response: Response<List<CheckBirth>>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as BirthSuccess
                    getSignupView.onSignupSuccess()
                }
                Log.d("checkREsp", response.toString())
            }

            override fun onFailure(call: Call<List<CheckBirth>>, t: Throwable) {
                Log.d("checkREsp", t.toString())
            }

        })


    }
}