package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.data.remote.req.BirthDate
import com.inha.hbc.data.remote.req.GetSignupData
import com.inha.hbc.databinding.FragmentSignup6Binding
import com.inha.hbc.ui.login.view.GetSignupView
import com.inha.hbc.util.fragmentmanager.SignupFragmentManager
import com.inha.hbc.util.network.RetrofitService

class Signup6Fragment: Fragment(), GetSignupView {
    lateinit var binding: FragmentSignup6Binding
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

        initData()
        initListener()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        initData()
    }

    fun initData() {
        binding.tvSignup6Name.text = SignupFragmentManager.signupData.name
        binding.tvSignup6Id.text = SignupFragmentManager.signupData.id
        binding.tvSignup6Phone.text = SignupFragmentManager.signupData.phone
        binding.tvSignup6Birth.text = SignupFragmentManager.signupData.year.toString() + "." +
                SignupFragmentManager.signupData.month.toString() + "." +
                SignupFragmentManager.signupData.day.toString()
    }

    fun initListener() {
        binding.ivSignup6Back.setOnClickListener {
            SignupFragmentManager.transaction(6, 5)
        }
        
        binding.tvSignup6Start.setOnClickListener {
            val birth = BirthDate(
                SignupFragmentManager.signupData.day!!,
                SignupFragmentManager.signupData.month!!, "SOLAR",
                SignupFragmentManager.signupData.year!!)
            val info = GetSignupData(birth,
                SignupFragmentManager.signupData.key!!,
                SignupFragmentManager.signupData.name!!,
                SignupFragmentManager.signupData.pw!!,
                SignupFragmentManager.signupData.pw!!,
                SignupFragmentManager.signupData.phone!!,
                SignupFragmentManager.signupData.id!!)
            RetrofitService().getSignup(info, this)
        }
    }

    override fun onSignupSuccess() {
        SignupFragmentManager.end()
    }

    override fun onSignupFailure() {
    }
}