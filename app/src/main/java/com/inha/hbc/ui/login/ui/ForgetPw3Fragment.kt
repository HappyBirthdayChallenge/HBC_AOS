package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inha.hbc.R
import com.inha.hbc.databinding.FragmentForgetPw3Binding

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
            findNavController().popBackStack()
        }
        binding.tvForgetPw3Next.setOnClickListener {
            findNavController().navigate(R.id.action_login_forget_pw3_to_login_norm_login)
        }
    }
}