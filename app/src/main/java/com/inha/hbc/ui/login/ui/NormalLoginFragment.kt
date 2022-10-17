package com.inha.hbc.ui.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.inha.hbc.R
import com.inha.hbc.data.local.Jwt
import com.inha.hbc.data.remote.req.NormSigninInfo
import com.inha.hbc.data.remote.resp.Data
import com.inha.hbc.data.remote.resp.NormSigninBody
import com.inha.hbc.databinding.FragmentNormalLoginBinding
import com.inha.hbc.ui.login.view.NormLoginView
import com.inha.hbc.ui.main.MainActivity
import com.inha.hbc.util.GlobalApplication
import com.inha.hbc.util.RetrofitService
import org.json.JSONObject

class NormalLoginFragment(val flId: Int): Fragment(), NormLoginView {
    private lateinit var binding: FragmentNormalLoginBinding
    private lateinit var parentContext: Context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNormalLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentContext = context
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val aniLogo = AnimationUtils.loadAnimation(parentContext, R.anim.login_logo_loading)
        val aniBtn = AnimationUtils.loadAnimation(parentContext, R.anim.login_btn_loading)

        binding.ivNormalLoginLogo.startAnimation(aniLogo)
        binding.tilNormalLoginId.startAnimation(aniBtn)
        binding.tilNormalLoginPw.startAnimation(aniBtn)
        binding.tvNormalLoginSignin.startAnimation(aniBtn)
        binding.tvNormalLoginSignup.startAnimation(aniBtn)

        initListener()
    }

    fun initListener() {
        binding.ivNormalLoginBack.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        binding.tvNormalLoginSignin.setOnClickListener {
            val id = binding.tieNormalLoginId.text.toString()
            val pw = binding.tieNormalLoginPw.text.toString()
            binding.lavNormalLoginLoading.visibility = View.VISIBLE
            RetrofitService().normSignin(NormSigninInfo(pw, id), this)
        }
    }

    fun decodeJwt(token: Data) {
        val gson = Gson()

        val accessString = String(Base64.decode(token.accessToken.split(".")[1], 0))
        val access = gson.fromJson(accessString, Jwt::class.java)

        GlobalApplication.prefs.setAccessJwt(access)


        val refreshString = String(Base64.decode(token.refreshToken.split(".")[1], 0))
        val refresh = gson.fromJson(refreshString, Jwt::class.java)

        GlobalApplication.prefs.setRefreshJwt(refresh)
    }

    fun isBirthAvailable(token: Jwt): Boolean {
        if (token.birth.date == -1) return false
        if (token.birth.month == -1) return false
        if (token.birth.year == -1) return false
        if (token.authorities.isEmpty()) return false
        if (token.authorities[0] == "ROLE_ASSOCIATE") return false
        return true
    }

    override fun onNormLoginSuccess(data: NormSigninBody) {
        GlobalApplication.prefs.setRealAccessJwt(data.token!!.accessToken)
        GlobalApplication.prefs.setRealRefreshJwt(data.token!!.accessToken)

        decodeJwt(data.token!!)

        val birth = isBirthAvailable(GlobalApplication.prefs.getAccessJwt())

        if (birth) {
            val intent = Intent(parentContext, MainActivity::class.java)
            startActivity(intent)
        }

        else {
            parentFragmentManager.beginTransaction().replace(flId, SetbirthFragment()).commit()
        }

    }

    override fun onNormLoginFailure(code: Int) {
        binding.lavNormalLoginLoading.visibility = View.INVISIBLE
        Toast.makeText(parentContext, code.toString() + "오류", Toast.LENGTH_SHORT).show()
    }
}