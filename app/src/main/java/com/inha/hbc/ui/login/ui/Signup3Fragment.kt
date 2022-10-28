package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.inha.hbc.databinding.FragmentSignup3Binding
import java.util.regex.Pattern

class Signup3Fragment: Fragment() {
    lateinit var binding: FragmentSignup3Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignup3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    fun initListener() {
        binding.ivSignup3Back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvSignup3Next.setOnClickListener {
            val name = binding.tieSignup3Pw.text.toString()
            if (checkValid(name)) {
                val args: Signup3FragmentArgs by navArgs()
                var data = args.userData
                data.name = name
                val action = Signup3FragmentDirections.actionLoginSignup3ToLoginSignup4(data)
                findNavController().navigate(action)
            }
        }
    }

    fun checkValid(name: String):Boolean {
        val namePattern = "^(?=.*[ㄱ-ㅎ|ㅏ-ㅣ|가-힣])(?=.*[A-Za-z])(?=.*[0-9])[[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]A-Za-z[0-9]]{2,20}$"
        val pattern = Pattern.compile(namePattern)
        val matcher = pattern.matcher(name)
        return matcher.find()
    }
}