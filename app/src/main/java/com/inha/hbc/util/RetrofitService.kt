package com.inha.hbc.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.inha.hbc.data.remote.req.*
import com.inha.hbc.data.remote.resp.*
import com.inha.hbc.ui.login.view.*
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.typeOf

class RetrofitService {
    lateinit var normLoginView: NormLoginView
    lateinit var kakaoLoginView: KakaoLoginView
    lateinit var checkIdView: CheckIdView
    lateinit var checkPhoneView: CheckPhoneView
    lateinit var checkCodeView: CheckCodeView
    lateinit var sendCodeView: SendCodeView
    lateinit var checkBirthView: CheckBirthView
    lateinit var getSignupView: GetSignupView
    lateinit var isMeView: IsMeView
    lateinit var findIdView: FindIdView
    lateinit var findPwView: FindPwView
    lateinit var getTokenView: GetTokenView

    fun callRetro(): RetroServiceInterface  {
        return NetworkModule.getRetrofit().create(RetroServiceInterface::class.java)
    }

    fun errToJson(resp: String): JsonObject {
        val errBody = resp
        val suberr = errBody.substring(1 until  errBody.length - 1)
        return JsonParser.parseString(suberr).asJsonObject
    }


    fun normSignin(data: NormSigninInfo, view: NormLoginView) {
        val normAuth = callRetro()
        normLoginView = view
        normAuth.normalSignin(data).enqueue(object : Callback<List<NormSignin>> {
            override fun onResponse(call: Call<List<NormSignin>>, response: Response<List<NormSignin>>) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as NormSuccess
                    if (resp.code  == "R-M011") {
                        normLoginView.onNormLoginSuccess(resp)
                    }
                    else {
                        normLoginView.onNormLoginFailure(resp)
                    }
                }
                else {
                    val errbody = Gson().fromJson(errToJson(response.errorBody()!!.string()), NormFailure::class.java)
                    normLoginView.onNormLoginFailure(errbody)
                }
            }

            override fun onFailure(call: Call<List<NormSignin>>, t: Throwable) {
                normLoginView.onNormLoginFailure("서버 오류")
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
                    val resp = response.body()!![0] as CheckIdSuccess
                    if (resp.code == "R-M001") {
                        checkIdView.onResponseSuccess(resp)
                    }
                    else {
                        checkIdView.onResponseFailure(resp)
                    }
                }
                else {
                    val err = Gson().fromJson(errToJson(response.errorBody()!!.string()), CheckIdFailure::class.java)
                    checkIdView.onResponseFailure(err)
                }
            }

            override fun onFailure(call: Call<List<CheckId>>, t: Throwable) {
                checkIdView.onResponseFailure("서버에러")
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

    fun reqCode(data: String, view: SendCodeView, category: String) {
        sendCodeView = view
        val body = CheckPhoneData(data, category)

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
                    val err = Gson().fromJson(errToJson(response.errorBody()!!.string()), CodeFailure::class.java)
                    checkCodeView.onCheckCodeResponseFailure(err)
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
                    if (resp.code == "R-M003") {
                        getSignupView.onSignupSuccess()
                    }
                    else {
                        getSignupView.onSignupFailure()
                    }
                }
                else {
                    getSignupView.onSignupFailure()
                }
            }

            override fun onFailure(call: Call<List<GetSignup>>, t: Throwable) {
                getSignupView.onSignupFailure()
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
                    checkBirthView.onBirthSuccess()
                }
                Log.d("checkREsp", response.toString())
            }

            override fun onFailure(call: Call<List<CheckBirth>>, t: Throwable) {
                Log.d("checkREsp", t.toString())
            }

        })


    }

    fun isMe(data: IsMeData, view: IsMeView) {
        isMeView = view

        callRetro().isMe(data).enqueue(object: Callback<List<IsMe>> {
            override fun onResponse(call: Call<List<IsMe>>, response: Response<List<IsMe>>) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as IsMeSuccess
                    if (resp.code == "R-M016") {
                        isMeView.onMeSuccess()
                    }
                    else {
                        isMeView.onMeFailure()
                    }
                }
                else {
                    isMeView.onMeFailure()
                }
            }

            override fun onFailure(call: Call<List<IsMe>>, t: Throwable) {
                Log.d("meResp", t.toString())
                isMeView.onMeFailure()
            }

        })
    }

    fun findId(data: FindIdData, view: FindIdView) {
        findIdView = view

        callRetro().findId(data).enqueue(object: Callback<List<FindId>> {
            override fun onResponse(call: Call<List<FindId>>, response: Response<List<FindId>>) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as FindIdSuccess
                    if (resp.code == "R-M008") {
                        findIdView.onFindIdSuccess(resp)
                    }
                    else {
                        findIdView.onFindIdFailure()
                    }
                }
                else {
                    findIdView.onFindIdFailure()
                }
            }

            override fun onFailure(call: Call<List<FindId>>, t: Throwable) {
                findIdView.onFindIdFailure()
            }

        })
    }

    fun findPw(data: FindPwData, view: FindPwView) {
        findPwView = view

        callRetro().findPw(data).enqueue(object: Callback<List<FindPw>>{
            override fun onResponse(call: Call<List<FindPw>>, response: Response<List<FindPw>>) {
                if (response.isSuccessful) {
                    val resp = response.body()!![0] as FindPwSuccess
                    if (resp.code == "R-M009") {
                        findPwView.onFindPwSuccess()
                    }
                    else {
                        findPwView.onFindPwFailure()
                    }
                }
                else {
                    findPwView.onFindPwFailure()
                }
            }

            override fun onFailure(call: Call<List<FindPw>>, t: Throwable) {
                findPwView.onFindPwFailure()
            }

        })
    }

    fun getToken(data: String, view: GetTokenView) {
        getTokenView = view
        val reData = "Bearer $data"

        callRetro().getToken(reData).enqueue(object: Callback<List<GetToken>> {
            override fun onResponse(
                call: Call<List<GetToken>>,
                response: Response<List<GetToken>>
            ) {
                if (response.isSuccessful) {

                    val resp = response.body()!![0] as GetTokenSuccess
                    if (resp.code == "R-J001") {
                        getTokenView.onGetTokenSuccess(resp)
                    }
                    else {
                        getTokenView.onGetTokenFailure()
                    }
                }
                else {
                    getTokenView.onGetTokenFailure()
                }
            }

            override fun onFailure(call: Call<List<GetToken>>, t: Throwable) {
                getTokenView.onGetTokenFailure()
            }

        })
    }
}