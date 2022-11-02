package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inha.hbc.data.local.SignupData
import com.inha.hbc.data.remote.resp.CheckIdFailure
import com.inha.hbc.data.remote.resp.CheckIdSuccess
import com.inha.hbc.databinding.FragmentForgetPw1Binding
import com.inha.hbc.ui.login.view.CheckIdView
import com.inha.hbc.util.RetrofitService
import java.util.regex.Pattern

class ForgetPw1Fragment: Fragment(), CheckIdView {
    lateinit var binding :FragmentForgetPw1Binding
    var id = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetPw1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    fun initListener() {
        binding.ivForgetPw1Back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvForgetPw1Next.setOnClickListener {
            id = binding.tieForgetPw1Id.text.toString()

            if (checkValid()) {
                binding.lavForgetPw1Loading.visibility = View.VISIBLE
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

    override fun onResponseSuccess(resp: CheckIdSuccess) {
        binding.lavForgetPw1Loading.visibility = View.GONE
        binding.tvForgetPw1Error.text = "아이디가 조회되지 않습니다."
    }

    override fun onResponseFailure(resp: CheckIdSuccess) {
        var data = SignupData()
        data.id = id
        val action = ForgetPw1FragmentDirections.actionLoginForgetPw1ToLoginForget1(data)
        binding.lavForgetPw1Loading.visibility = View.GONE
        findNavController().navigate(action)
    }

    override fun onResponseFailure(resp: CheckIdFailure) {
        binding.lavForgetPw1Loading.visibility = View.GONE
        binding.tvForgetPw1Error.text = resp.message + resp.errors[0]!!.reason
    }

    override fun onResponseFailure(message: String) {
        binding.lavForgetPw1Loading.visibility = View.GONE
        binding.tvForgetPw1Error.text = message
    }

}