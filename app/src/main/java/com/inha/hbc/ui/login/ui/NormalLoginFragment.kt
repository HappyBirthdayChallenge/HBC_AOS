package com.inha.hbc.ui.login.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.inha.hbc.R
import com.inha.hbc.data.local.Jwt
import com.inha.hbc.data.local.SignupData
import com.inha.hbc.data.remote.req.NormSigninInfo
import com.inha.hbc.data.remote.resp.Data
import com.inha.hbc.data.remote.resp.NormFailure
import com.inha.hbc.data.remote.resp.NormSignin
import com.inha.hbc.data.remote.resp.NormSuccess
import com.inha.hbc.databinding.FragmentNormalLoginBinding
import com.inha.hbc.ui.login.view.NormLoginView
import com.inha.hbc.ui.main.MainActivity
import com.inha.hbc.util.GlobalApplication
import com.inha.hbc.util.RetrofitService
import java.util.regex.Pattern

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

        initListener()
    }

    fun initListener() {

        binding.ivNormalLoginBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvNormalLoginSignin.setOnClickListener {
            val id = binding.tieNormalLoginId.text.toString()
            val pw = binding.tieNormalLoginPw.text.toString()
            if (!checkIdValid(id)) {
                binding.tvNormalLoginError.text = "id가 형식에 맞지 않습니다."
                return@setOnClickListener
            }

            if(!checkPwValid(pw)){
                binding.tvNormalLoginError.text = "pw가 형식에 맞지 않습니다."
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
            var data = SignupData()
            val action = NormalLoginFragmentDirections.actionLoginNormLoginToLoginForget1(data)

            binding.tvNormalLoginForgetBackground.visibility = View.GONE
            binding.clNormalLoginForgetDialog.visibility = View.GONE

            findNavController().navigate(action)
        }

        binding.tvNormalLoginForgetPw.setOnClickListener {
            binding.tvNormalLoginForgetBackground.visibility = View.GONE
            binding.clNormalLoginForgetDialog.visibility = View.GONE

            findNavController().navigate(R.id.action_login_norm_login_to_login_forget_pw1)
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

    override fun onNormLoginSuccess(data: NormSuccess) {

        val intent = Intent(parentContext, MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onNormLoginFailure(data: NormSuccess) {
        binding.lavNormalLoginLoading.visibility = View.GONE
        binding.tvNormalLoginError.text = data.message

    }

    override fun onNormLoginFailure(data: NormFailure) {
        binding.lavNormalLoginLoading.visibility = View.GONE
        binding.tvNormalLoginError.text = data.message  + ", " + if (data.errors.isNotEmpty()) {
            data.errors[0].reason
        }
        else {
            ""
        }
    }

    override fun onNormLoginFailure(message: String) {
        binding.tvNormalLoginError.text = message
    }
}