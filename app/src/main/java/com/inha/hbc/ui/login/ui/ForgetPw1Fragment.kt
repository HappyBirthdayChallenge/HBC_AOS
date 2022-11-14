package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.data.remote.resp.CheckIdFailure
import com.inha.hbc.data.remote.resp.CheckIdSuccess
import com.inha.hbc.databinding.FragmentForgetPw1Binding
import com.inha.hbc.ui.login.view.CheckIdView
import com.inha.hbc.util.fragmentmanager.NormLoginFragmentManager
import com.inha.hbc.util.network.RetrofitService
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

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        binding.tvForgetPw1Error.text = ""
    }

    fun initListener() {
        binding.ivForgetPw1Back.setOnClickListener {
            NormLoginFragmentManager.forgetBackPressed()
        }
        binding.tvForgetPw1Next.setOnClickListener {
            id = binding.tieForgetPw1Id.text.toString()

            if (checkValid()) {
                binding.lavForgetPw1Loading.visibility = View.VISIBLE
                RetrofitService().checkId(id, this)
            }
            else {
                binding.tvForgetPw1Error.visibility = View.VISIBLE
                binding.tvForgetPw1Error.text = "아이디는 5~20자의 영문 대/소문자, 숫자만 사용하여 작성해 주세요."
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
        binding.tvForgetPw1Error.text = "일치하는 회원이 없어요."
    }

    override fun onResponseFailure(resp: CheckIdSuccess) {
        NormLoginFragmentManager.data.id = id
        binding.lavForgetPw1Loading.visibility = View.GONE
        NormLoginFragmentManager.transaction(1, 2)
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