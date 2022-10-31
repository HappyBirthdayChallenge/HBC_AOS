package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inha.hbc.R
import com.inha.hbc.databinding.FragementForget1Binding

class Forget1Fragment: Fragment() {
    lateinit var binding: FragementForget1Binding
    var name = ""
    var phone = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragementForget1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    fun initListener() {
        binding.tvForget1Next.setOnClickListener {
            name = binding.tieForget1Name.text.toString()
            phone = binding.tieForget1PwPhone.text.toString()

            findNavController().navigate(R.id.action_login_forget1_to_login_forget2)
        }
    }
}