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
import com.inha.hbc.data.remote.resp.Data
import com.inha.hbc.data.remote.resp.GetTokenSuccess
import com.inha.hbc.databinding.FragmentLoginBinding
import com.inha.hbc.ui.login.view.GetTokenView
import com.inha.hbc.ui.login.view.KakaoLoginView
import com.inha.hbc.ui.main.MainActivity
import com.inha.hbc.util.fragmentmanager.NormLoginFragmentManager
import com.inha.hbc.util.sharedpreference.GlobalApplication
import com.inha.hbc.util.fragmentmanager.SignupFragmentManager
import com.inha.hbc.util.network.RetrofitService
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class LoginFragment: Fragment(), KakaoLoginView, GetTokenView {

    lateinit var binding: FragmentLoginBinding
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

        checkToken()

        initListener()

        afterSplash()
    }
    fun initListener() {
        binding.clLoginKakaoLogin.setOnClickListener {
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
        val refreshJwt = GlobalApplication.prefs.getRealRefreshJwt()
        if (refreshJwt.isNullOrEmpty()) {
        }
        else {
            binding.lavLoginLoading.visibility = View.VISIBLE
            RetrofitService().getToken(refreshJwt, this)
        }
    }



    fun getToken(token: OAuthToken) {
        binding.lavLoginLoading.visibility = View.VISIBLE
        RetrofitService().kakaoSignin("KAKAO", token.accessToken, this)

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

    fun isBirthAvailable(token: Jwt): Boolean {
        if (token.birth.date == -1) return false
        if (token.birth.month == -1) return false
        if (token.birth.year == -1) return false
        return true
    }

    override fun onKakaoLoginSuccess(data: Data) {
        decodeJwt(data)
        if (isBirthAvailable(GlobalApplication.prefs.getAccessJwt())) {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        else {
            binding.lavLoginLoading.visibility = View.GONE

            parentFragmentManager.beginTransaction().replace(NormLoginFragmentManager.frameId, KakaoBirthFragment()).commit()
        }
    }

    override fun onKakaoLoginFailure(code: Int) {
        binding.lavLoginLoading.visibility = View.GONE
        Toast.makeText(context, "$code 에러", Toast.LENGTH_SHORT).show()
    }



    override fun onGetTokenSuccess(resp: GetTokenSuccess) {
        decodeJwt(resp.data!!)
        binding.lavLoginLoading.visibility = View.GONE
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()


    }

    override fun onGetTokenFailure() {
        binding.lavLoginLoading.visibility = View.GONE
    }
}