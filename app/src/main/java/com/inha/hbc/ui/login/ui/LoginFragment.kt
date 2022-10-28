package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inha.hbc.R
import com.inha.hbc.data.remote.resp.NormSignin
import com.inha.hbc.databinding.FragmentLoginBinding
import com.inha.hbc.util.RetrofitService
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class LoginFragment: Fragment() {

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

        initListener()

        afterSplash()
    }
    fun initListener() {
        binding.clLoginKakaoLogin.setOnClickListener {
         //   kakaologin()
        }

        binding.tvLoginNormalLogin.setOnClickListener {
            findNavController().navigate(R.id.action_login_main_to_login_norm_login)
        }

        binding.tvLoginSignup.setOnClickListener {
            findNavController().navigate(R.id.action_login_main_to_login_signup1)
        }
    }

    fun afterSplash() {
        val aniLogo = AnimationUtils.loadAnimation(context, R.anim.login_logo_loading)
        val aniBtn = AnimationUtils.loadAnimation(context, R.anim.login_btn_loading)

        binding.ivLoginLogo.startAnimation(aniLogo)
        binding.clLoginKakaoLogin.startAnimation(aniBtn)
        binding.tvLoginNormalLogin.startAnimation(aniBtn)

    }
//    fun kakaologin() {
//        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) { //카카오톡 앱 깔려있을 경우
//            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
//                if (error != null) {
//                    Log.d("kakaoErr", error.toString())
//                }
//                else if (token != null){
//                    getToken(token!!)
//                }
//            }
//        }
//        else {
//            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
//                if (error != null) {
//                    Log.d("kakaoErr", error.toString())
//                }
//                else if (token != null) {
//                    getToken(token!!)
//                }
//            }
//        }
//    }
//
//    fun getToken(token: OAuthToken) {
//        binding.lavLoginLoading.visibility = View.VISIBLE
//        Log.d("kakaoTok", token.toString())
//        RetrofitService().kakaoSignin("KAKAO", token.accessToken, this)
//
//    }
//
//    override fun onNormLoginSuccess(data: NormSignin) {
//    }
//
//    override fun onNormLoginFailure(code: Int) {
//        Toast.makeText(this, "$code 에러", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onKakaoLoginSuccess() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onKakaoLoginFailure(code: Int) {
//        Toast.makeText(this, "$code 에러", Toast.LENGTH_SHORT).show()
//    }
}