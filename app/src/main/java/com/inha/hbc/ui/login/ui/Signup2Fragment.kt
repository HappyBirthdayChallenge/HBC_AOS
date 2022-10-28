package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inha.hbc.databinding.FragmentSignup2Binding

class Signup2Fragment: Fragment() {
    lateinit var binding: FragmentSignup2Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignup2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    fun initListener() {
        binding.ivSignup2Back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvSignup2Next.setOnClickListener {

        }
    }

    fun checkValid() {
        val idPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{5,20}$"
    }
}