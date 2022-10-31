package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inha.hbc.data.local.SignupData
import com.inha.hbc.databinding.FragmentSignup1Binding
import com.inha.hbc.ui.login.view.CheckIdView
import com.inha.hbc.util.RetrofitService
import java.util.regex.Pattern

class Signup1Fragment: Fragment(), CheckIdView {
    lateinit var binding: FragmentSignup1Binding
    var id = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignup1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    fun initListener() {
        binding.ivSignup1Back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvSignup1Next.setOnClickListener {
            id = binding.tieSignup1Id.text.toString()
            if (checkValid()) {
                binding.lavSignup1Loading.visibility = View.VISIBLE
                RetrofitService().checkId(id, this)
            }
        }
    }

    fun checkValid(): Boolean {
        val idPattern = "^[A-Za-z\\d]{5,20}\$"
        val pattern = Pattern.compile(idPattern)
        val matcher = pattern.matcher(id)
        return matcher.find()
    }

    override fun onResponseSuccess() {
        id = binding.tieSignup1Id.text.toString()
        var data = SignupData()
        data.id = id
        val action = Signup1FragmentDirections.actionLoginSignup1ToLoginSignup2(data)

        binding.lavSignup1Loading.visibility = View.GONE
        findNavController().navigate(action)
    }

    override fun onResponseFailure() {
        binding.tvSignup1Error.text
    }
}