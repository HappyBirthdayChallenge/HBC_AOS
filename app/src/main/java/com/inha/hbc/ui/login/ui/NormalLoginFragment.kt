package com.inha.hbc.ui.login.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.inha.hbc.R
import com.inha.hbc.data.local.Jwt
import com.inha.hbc.data.remote.req.NormSigninInfo
import com.inha.hbc.data.remote.resp.Data
import com.inha.hbc.data.remote.resp.NormSignin
import com.inha.hbc.databinding.FragmentNormalLoginBinding
import com.inha.hbc.ui.login.view.NormLoginView
import com.inha.hbc.util.GlobalApplication
import com.inha.hbc.util.RetrofitService

class NormalLoginFragment(): Fragment(), NormLoginView {
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

//        binding.tilNormalLoginId.startAnimation(aniBtn)
//        binding.tilNormalLoginPw.startAnimation(aniBtn)
//        binding.tvNormalLoginSignin.startAnimation(aniBtn)

        initListener()
    }

    fun initListener() {

        binding.tvNormalLoginSignin.setOnClickListener {
            val id = binding.tieNormalLoginId.text.toString()
            val pw = binding.tieNormalLoginPw.text.toString()
            binding.lavNormalLoginLoading.visibility = View.VISIBLE
            RetrofitService().normSignin(NormSigninInfo(pw, id), this)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onNormLoginSuccess(data: NormSignin) {
//        GlobalApplication.prefs.setRealAccessJwt(data.token!!.accessToken)
//        GlobalApplication.prefs.setRealRefreshJwt(data.token!!.accessToken)
//
//        if (Build.VERSION_CODES.O <= BuildConfig.VERSION_CODE) {
//            decodeJwt(data.token!!)
//        }
//        else {
//        }
//
//        val birth = isBirthAvailable(GlobalApplication.prefs.getAccessJwt())
//
//        if (birth) {
//            val intent = Intent(parentContext, MainActivity::class.java)
//            startActivity(intent)
//        }
//
//        else {
//            parentFragmentManager.beginTransaction().replace(flId, SetbirthFragment()).commit()
//        }

    }

    override fun onNormLoginFailure(code: Int) {
//        binding.lavNormalLoginLoading.visibility = View.INVISIBLE
//        Toast.makeText(parentContext, code.toString() + "오류", Toast.LENGTH_SHORT).show()

    }
}