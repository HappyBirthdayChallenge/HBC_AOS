package com.inha.hbc.ui.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.inha.hbc.data.local.SignupData
import com.inha.hbc.data.remote.req.BirthDate
import com.inha.hbc.data.remote.req.GetSignupData
import com.inha.hbc.databinding.FragmentSignup6Binding
import com.inha.hbc.ui.login.view.GetSignupView
import com.inha.hbc.ui.main.MainActivity
import com.inha.hbc.util.RetrofitService

class Signup6Fragment: Fragment(), GetSignupView {
    lateinit var binding: FragmentSignup6Binding
    lateinit var data: SignupData
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignup6Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initData()
    }

    fun initData() {
        val args : Signup6FragmentArgs by navArgs()
        data = args.userData
        binding.tvSignup6Name.text = data.name
        binding.tvSignup6Id.text = data.id
        binding.tvSignup6Phone.text = data.phone
        binding.tvSignup6Birth.text = data.year.toString() + "." + data.month.toString() + "." + data.day.toString()
    }

    fun initListener() {
        binding.ivSignup6Back.setOnClickListener {
            findNavController().popBackStack()
        }
        
        binding.tvSignup6Start.setOnClickListener {
            val birth = BirthDate(data.day!!, data.month!!, "SOLAR", data.year!!)
            val info = GetSignupData(birth, data.key!!, data.name!!, data.pw!!, data.pw!!, data.phone!!, data.id!!)
            RetrofitService().getSignup(info, this)
        }
    }

    override fun onSignupSuccess() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onSignupFailure() {
        TODO("Not yet implemented")
    }
}