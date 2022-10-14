package com.inha.hbc.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.inha.hbc.R
import com.inha.hbc.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class LoginActivity: AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()


        afterSplash()

        var keyHash = Utility.getKeyHash(this)
        Log.d("keyhash", keyHash)
    }

    fun initListener() {
        binding.ivLoginKakaoLogin.setOnClickListener {
            kakaologin()
        }

        binding.tvLoginNormalLogin.setOnClickListener {
        }
    }

    fun afterSplash() {
        val aniLogo = AnimationUtils.loadAnimation(applicationContext, R.anim.login_logo_loading)
        val aniBtn = AnimationUtils.loadAnimation(applicationContext, R.anim.login_btn_loading)

        binding.ivLoginLogo.startAnimation(aniLogo)
        binding.ivLoginKakaoLogin.startAnimation(aniBtn)
        binding.tvLoginNormalLogin.startAnimation(aniBtn)

    }
    fun kakaologin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) { //카카오톡 앱 깔려있을 경우
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.d("kakaoErr", error.toString())
                }
                else if (token != null){
                    getToken(token!!)
                }
            }
        }
        else {
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                if (error != null) {
                    Log.d("kakaoErr", error.toString())
                }
                else if (token != null) {
                    getToken(token!!)
                }
            }
        }
    }

    fun getToken(token: OAuthToken) {
        binding.lavLoginLoading.visibility = View.VISIBLE
        Log.d("kakaoTok", token.toString())

    }
}