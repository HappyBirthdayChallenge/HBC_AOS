package com.inha.hbc.ui.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.inha.hbc.R
import com.inha.hbc.data.remote.req.NormSigninInfo
import com.inha.hbc.databinding.FragmentNormalLoginBinding
import com.inha.hbc.ui.login.view.NormLoginView
import com.inha.hbc.ui.main.MainActivity
import com.inha.hbc.util.RetrofitService
//아이디1234
//비밀번호1@
class NormalLoginFragment: Fragment(), NormLoginView {
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

    override fun onNormLoginSuccess() {
        val intent = Intent(parentContext, MainActivity::class.java)
        binding.lavNormalLoginLoading.visibility = View.INVISIBLE
        startActivity(intent)
    }

    override fun onNormLoginFailure(code: Int) {
        binding.lavNormalLoginLoading.visibility = View.INVISIBLE
        Toast.makeText(parentContext, code.toString() + "오류", Toast.LENGTH_SHORT).show()
    }
}