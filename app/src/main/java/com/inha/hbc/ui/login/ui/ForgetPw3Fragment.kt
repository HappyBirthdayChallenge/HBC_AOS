package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentForgetPw3Binding
import com.inha.hbc.util.fragmentmanager.NormLoginFragmentManager

class ForgetPw3Fragment: Fragment() {
    lateinit var binding: FragmentForgetPw3Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetPw3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    fun initListener() {
        binding.ivForgetPw3Back.setOnClickListener {
            NormLoginFragmentManager.forgetBackPressed()
        }
        binding.tvForgetPw3Next.setOnClickListener {
            NormLoginFragmentManager.end()
        }
    }
}