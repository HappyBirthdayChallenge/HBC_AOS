package com.inha.hbc.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.inha.hbc.R
import com.inha.hbc.data.remote.req.NormSigninInfo
import com.inha.hbc.databinding.FragmentNormalLoginBinding
import com.inha.hbc.util.RetrofitService

class NormalLoginFragment: Fragment() {
    private lateinit var binding: FragmentNormalLoginBinding
    private lateinit var mainContext: Context
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
        mainContext = context
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val aniLogo = AnimationUtils.loadAnimation(mainContext, R.anim.login_logo_loading)
        val aniBtn = AnimationUtils.loadAnimation(mainContext, R.anim.login_btn_loading)

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
            RetrofitService().normSignin(NormSigninInfo(pw, id))
        }
    }
}