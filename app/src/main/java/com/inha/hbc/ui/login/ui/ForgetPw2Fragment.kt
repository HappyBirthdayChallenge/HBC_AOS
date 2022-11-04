package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.inha.hbc.R
import com.inha.hbc.data.remote.req.FindPwData
import com.inha.hbc.databinding.FragmentForgetPw2Binding
import com.inha.hbc.ui.login.view.FindPwView
import com.inha.hbc.util.NormLoginFragmentManager
import com.inha.hbc.util.RetrofitService

class ForgetPw2Fragment: Fragment(), FindPwView {
    lateinit var binding: FragmentForgetPw2Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetPw2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    fun initListener() {
        binding.ivForgetPw2Back.setOnClickListener {
            NormLoginFragmentManager.forgetBackPressed()
        }
        binding.tvForgetPw2Next.setOnClickListener {
            binding.lavForgetPw2Loading.visibility = View.VISIBLE
            val pw = binding.tieForgetPw2Pw.text.toString()
            val pwConfirm = binding.tieForgetPw2PwConfirm.text.toString()

            if (pw == pwConfirm) {
                val data = NormLoginFragmentManager.data
                val reqData = FindPwData(data.key!!, data.name!!, pw, pwConfirm, data.phone!!, data.id!!)
                RetrofitService().findPw(reqData, this)
            }
        }
    }

    override fun onFindPwSuccess() {
        binding.lavForgetPw2Loading.visibility = View.GONE

        NormLoginFragmentManager.transaction(4, 5)

    }

    override fun onFindPwFailure() {
        binding.lavForgetPw2Loading.visibility = View.GONE
    }
}