package com.inha.hbc.ui.login.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.inha.hbc.R
import com.inha.hbc.data.local.Jwt
import com.inha.hbc.data.remote.req.KakaoSigninInfo
import com.inha.hbc.data.remote.resp.*
import com.inha.hbc.databinding.FragmentLoginBinding
import com.inha.hbc.ui.login.view.*
import com.inha.hbc.ui.main.MainActivity
import com.inha.hbc.util.firebase.FirebaseMessagingService
import com.inha.hbc.util.fragmentmanager.NormLoginFragmentManager
import com.inha.hbc.util.sharedpreference.GlobalApplication
import com.inha.hbc.util.fragmentmanager.SignupFragmentManager
import com.inha.hbc.util.network.RetrofitService
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class LoginFragment(val isBackBirth: Boolean): Fragment(), KakaoLoginView, GetTokenView, CheckTokenView, GetMyInfoView, RefreshFcmView {

    lateinit var binding: FragmentLoginBinding
    var auto = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isBackBirth) {
            checkToken()
        }

        initListener()

        afterSplash()
    }
    fun initListener() {
        binding.clLoginKakaoLogin.setOnClickListener {
            auto = false
            kakaologin()
        }

        binding.tvLoginNormalLogin.setOnClickListener {
            NormLoginFragmentManager.normloginStart()
        }

        binding.tvLoginSignup.setOnClickListener {
            SignupFragmentManager.start()
        }

    }

    fun afterSplash() {
        val aniLogo = AnimationUtils.loadAnimation(context, R.anim.login_logo_loading)
        val aniBtn = AnimationUtils.loadAnimation(context, R.anim.login_btn_loading)

        binding.ivLoginLogo.startAnimation(aniLogo)
        binding.clLoginKakaoLogin.startAnimation(aniBtn)
        binding.tvLoginNormalLogin.startAnimation(aniBtn)
        binding.tvLoginSignup.startAnimation(aniBtn)

    }
    fun kakaologin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) { //카카오톡 앱 깔려있을 경우
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                if (error != null) {
                    Log.d("kakaoErr", error.toString())
                }
                else if (token != null){
                    getToken(token!!)
                }
                else {
                    Toast.makeText(context, "에러 발생", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext()) { token, error ->
                if (error != null) {
                    Log.d("kakaoErr", error.toString())
                }
                else if (token != null) {
                    getToken(token!!)
                }
                else {
                    Toast.makeText(context, "에러 발생", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun checkToken() {
        val accessJwt = GlobalApplication.prefs.getRealAccessJwt()
        if (!accessJwt.isNullOrEmpty()) {
            binding.lavLoginLoading.visibility = View.VISIBLE
            RetrofitService().checkToken(this)
        }
    }



    fun getToken(token: OAuthToken) {
        binding.lavLoginLoading.visibility = View.VISIBLE
        val data = KakaoSigninInfo(GlobalApplication.prefs.getFcmtoken()!!,
        token.accessToken, "KAKAO")
        RetrofitService().kakaoSignin(data, this)

    }

    fun decodeJwt(token: Data) {

        var gson = Gson()
        val accessDecode = java.util.Base64.getUrlDecoder().decode(token.accessToken.split(".")[1])
        val accessString = String(accessDecode, Charsets.UTF_8)
        val access = gson.fromJson(accessString, Jwt::class.java)

        GlobalApplication.prefs.setAccessJwt(access)
        GlobalApplication.prefs.setRealAccessJwt(token.accessToken)


        val refreshDecode = java.util.Base64.getUrlDecoder().decode(token.refreshToken.split(".")[1])
        val refreshString = String(refreshDecode, Charsets.UTF_8)
        val refresh = gson.fromJson(refreshString, Jwt::class.java)

        GlobalApplication.prefs.setRefreshJwt(refresh)
        GlobalApplication.prefs.setRealRefreshJwt(token.refreshToken)

    }

    fun isBirthAvailable(data: GetMyInfoBirth): Boolean {
        if (data.date == -1) return false
        if (data.month == -1) return false
        if (data.year == -1) return false
        return true
    }

    override fun onKakaoLoginSuccess(data: Data) {
        decodeJwt(data)
        RetrofitService().getMyInfo(this)
    }

    override fun onKakaoLoginFailure(code: Int) {
        binding.lavLoginLoading.visibility = View.GONE
        Toast.makeText(context, "$code 에러", Toast.LENGTH_SHORT).show()
    }



    override fun onGetTokenSuccess(resp: GetTokenSuccess) {
        decodeJwt(resp.data!!)
        RetrofitService().getMyInfo(this)
    }

    override fun onGetTokenFailure() {
        binding.lavLoginLoading.visibility = View.GONE
    }

    override fun onCheckTokenSuccess() {
        RetrofitService().getMyInfo(this)
    }

    override fun onCheckTokenFailure() {
        val refreshJwt = GlobalApplication.prefs.getRealRefreshJwt()
        if (refreshJwt.isNullOrEmpty()) {
        }
        else {
            binding.lavLoginLoading.visibility = View.VISIBLE
            RetrofitService().getToken(refreshJwt, this)
        }
    }

    override fun onGetMyInfoSuccess(resp: GetMyInfoSuccess) {
        if (isBirthAvailable(resp.data!!.birth_date)) {
            RetrofitService().refreshFcm(GlobalApplication.prefs.getFcmtoken()!!)
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        else {
            binding.lavLoginLoading.visibility = View.GONE
            if (!auto) {
                parentFragmentManager.beginTransaction().replace(NormLoginFragmentManager.frameId, KakaoBirthFragment()).commit()
            }

        }
    }

    override fun onGetMyInfoFailure() {
        TODO("Not yet implemented")
    }

    override fun onRefreshFcmSuccess() {
    }

    override fun onRefreshFcmFailure() {
        TODO("Not yet implemented")
    }


}