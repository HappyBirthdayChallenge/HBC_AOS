package com.inha.hbc.ui.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.inha.hbc.R
import com.inha.hbc.data.local.Jwt
import com.inha.hbc.data.remote.req.NormSigninInfo
import com.inha.hbc.data.remote.resp.Data
import com.inha.hbc.data.remote.resp.NormFailure
import com.inha.hbc.data.remote.resp.NormSuccess
import com.inha.hbc.databinding.FragmentNormalLoginBinding
import com.inha.hbc.ui.login.view.NormLoginView
import com.inha.hbc.ui.main.MainActivity
import com.inha.hbc.util.GlobalApplication
import com.inha.hbc.util.NormLoginFragmentManager
import com.inha.hbc.util.RetrofitService
import java.util.regex.Pattern

class NormalLoginFragment(): Fragment(), NormLoginView {
    lateinit var callback: OnBackPressedCallback
    private lateinit var binding: FragmentNormalLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNormalLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    fun initListener() {

        binding.ivNormalLoginBack.setOnClickListener {
            NormLoginFragmentManager.baseBackPressed()
        }

        binding.tvNormalLoginSignin.setOnClickListener {
            val id = binding.tieNormalLoginId.text.toString()
            val pw = binding.tieNormalLoginPw.text.toString()
            if (!checkIdValid(id)) {
                binding.tvNormalLoginError.text = "입력하신 아이디 또는 비밀번호가 일치하지 않아요"
                return@setOnClickListener
            }

            if(!checkPwValid(pw)){
                binding.tvNormalLoginError.text = "입력하신 아이디 또는 비밀번호가 일치하지 않아요"
                return@setOnClickListener
            }
            binding.lavNormalLoginLoading.visibility = View.VISIBLE
            RetrofitService().normSignin(NormSigninInfo(pw, id), this)
        }

        binding.tvNormalLoginForget.setOnClickListener {
            binding.tvNormalLoginForgetBackground.visibility = View.VISIBLE
            binding.clNormalLoginForgetDialog.visibility = View.VISIBLE
        }

        binding.tvNormalLoginForgetBackground.setOnClickListener {
            binding.tvNormalLoginForgetBackground.visibility = View.GONE
            binding.clNormalLoginForgetDialog.visibility = View.GONE
        }

        binding.tvNormalLoginForgetId.setOnClickListener {
            binding.tvNormalLoginForgetBackground.visibility = View.GONE
            binding.clNormalLoginForgetDialog.visibility = View.GONE

            NormLoginFragmentManager.idStart()
        }

        binding.tvNormalLoginForgetPw.setOnClickListener {
            binding.tvNormalLoginForgetBackground.visibility = View.GONE
            binding.clNormalLoginForgetDialog.visibility = View.GONE

            NormLoginFragmentManager.pwstart()
        }
    }

    fun checkIdValid(id: String): Boolean {
        val idPattern = "^[A-Za-z\\d]{5,20}\$"
        val pattern = Pattern.compile(idPattern)
        val matcher = pattern.matcher(id)
        return matcher.find()
    }

    fun checkPwValid(pw: String):Boolean {
        val pwPattern = "^(?=.*[`~!@#$%^&*()])(?=.*[A-Za-z])(?=.*[0-9])[[`~!@#$%^&*()]A-Za-z[0-9]]{10,20}$"
        val pattern = Pattern.compile(pwPattern)
        val matcher = pattern.matcher(pw)
        return matcher.find()
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

    override fun onNormLoginSuccess(data: NormSuccess) {

        decodeJwt(data.token)

        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onNormLoginFailure(data: NormSuccess) {
        binding.lavNormalLoginLoading.visibility = View.GONE
        binding.tvNormalLoginError.text = data.message

    }

    override fun onNormLoginFailure(data: NormFailure) {
        binding.lavNormalLoginLoading.visibility = View.GONE
        binding.tvNormalLoginError.text = "입력하신 아이디 또는 비밀번호가 일치하지 않아요"
    }

    override fun onNormLoginFailure(message: String) {
        binding.tvNormalLoginError.text = message
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                NormLoginFragmentManager.forgetBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }


    override fun onDetach() {
        super.onDetach()
        callback.remove()

    }
}