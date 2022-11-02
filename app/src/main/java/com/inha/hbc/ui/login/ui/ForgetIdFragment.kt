package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.inha.hbc.R
import com.inha.hbc.databinding.FragmentForgetIdBinding

class ForgetIdFragment: Fragment() {
    lateinit var binding: FragmentForgetIdBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetIdBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initView()
    }

    fun initView() {
        val arg : ForgetIdFragmentArgs by navArgs()
        binding.tvForgetIdId.text = arg.forgetData.id
    }

    fun initListener() {
        binding.ivForgetIdBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvForgetIdNext.setOnClickListener {
            findNavController().navigate(R.id.action_login_forget_id_to_login_norm_login)
        }
    }
}