package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentForgetIdBinding
import com.inha.hbc.util.fragmentmanager.NormLoginFragmentManager

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
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        binding.tvForgetIdError.text = ""
        initView()
    }
    fun initView() {
        binding.tvForgetIdId.text = NormLoginFragmentManager.data.id
    }

    fun initListener() {
        binding.ivForgetIdBack.setOnClickListener {
            NormLoginFragmentManager.forgetBackPressed()
        }
        binding.tvForgetIdNext.setOnClickListener {
            NormLoginFragmentManager.end()
        }
    }
}